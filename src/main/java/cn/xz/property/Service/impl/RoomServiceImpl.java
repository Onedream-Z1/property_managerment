package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.RoomService;
import cn.xz.property.Service.UnitService;
import cn.xz.property.Service.UserService;
import cn.xz.property.dto.RoomDto;
import cn.xz.property.dto.UnitDto;
import cn.xz.property.entity.Building;
import cn.xz.property.entity.Room;
import cn.xz.property.entity.Unit;
import cn.xz.property.entity.User;
import cn.xz.property.mapper.RoomMapper;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;

    @Override
    public R pageListInfo(Integer pn,Integer total,Integer status) {
        Page<Room> pageInfo=new Page<>(pn,total);
        Page<RoomDto> pageInfoDto=new Page<>(pn,total);
        LambdaQueryWrapper<Room> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(status!=null,Room::getStatus,status);
        page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<Room> records = pageInfo.getRecords();
        List<RoomDto> roomDtos = records.stream().map((items) -> {
            RoomDto roomDto = new RoomDto();
            BeanUtils.copyProperties(items, roomDto);

            Integer userId = items.getUserId();
            int belongUnit = items.getBelongUnit();
            Unit unit = unitService.getById(belongUnit);
            if (unit!=null){ roomDto.setUnitName(unit.getName()); }
            final LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(userId>0, User::getUserId,userId);
            User user = userService.getOne(queryWrapper,false);
            if(userId>0){
                if (user != null) {
                    roomDto.setUsername(user.getUsername());
                    roomDto.setPhone(user.getPhone());
                    roomDto.setSex(user.getSex());
                    roomDto.setAddress(user.getAddress());
                }
            }
            return roomDto;
        }).collect(Collectors.toList());
        pageInfoDto.setRecords(roomDtos);
        return R.success(pageInfoDto);
    }

    @Override
    public R saveRoom(Room room) {
        boolean isSuccess = save(room);
        if(!isSuccess){
            return R.error("保存失败！");
        }
        return R.success("保存成功！");
    }

    @Override
    public R updateRoom(Room room) {
        boolean isSuccess = updateById(room);
        if(!isSuccess){
            return R.error("保存失败！");
        }
        return R.success("保存成功！");
    }
}
