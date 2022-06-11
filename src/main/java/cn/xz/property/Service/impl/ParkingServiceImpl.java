package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.ParkingService;
import cn.xz.property.Service.UserService;
import cn.xz.property.dto.ParkingDto;
import cn.xz.property.entity.Parking;
import cn.xz.property.entity.User;
import cn.xz.property.mapper.ParkingMapper;
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
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {

    @Autowired
    private UserService userService;

    @Override
    public R palistInfo(Integer pn, Integer total, Integer status) {

        Page<Parking> pageInfo=new Page<>(pn,total);
        Page<ParkingDto> pageInfoDto=new Page<>(pn,total);
        LambdaQueryWrapper<Parking> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(status!=null,Parking::getStatus,status);
        page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<Parking> records = pageInfo.getRecords();
        List<ParkingDto> parkingDtos = records.stream().map((items) -> {
            ParkingDto parkingDto = new ParkingDto();
            BeanUtils.copyProperties(items, parkingDto);

            int userId = items.getUserId();
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(userId>0, User::getUserId,userId);
            User user = userService.getOne(queryWrapper,false);
            if(userId>0){
                if (user != null) {
                    parkingDto.setUsername(user.getUsername());
                    parkingDto.setPhone(user.getPhone());
                    parkingDto.setSex(user.getSex());
                    parkingDto.setAddress(user.getAddress());
                }
            }
            return parkingDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(parkingDtos);

        return R.success(pageInfoDto);

    }
}
