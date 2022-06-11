package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.RepairService;
import cn.xz.property.Service.UserService;
import cn.xz.property.dto.ParkingDto;
import cn.xz.property.dto.RepairDto;
import cn.xz.property.entity.Parking;
import cn.xz.property.entity.Repair;
import cn.xz.property.entity.User;
import cn.xz.property.mapper.RepairMapper;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    @Autowired
    private UserService userService;

    @Override
    public R pageListInfo(Integer pn, Integer total, Integer type) {

        Page<Repair> pageInfo=new Page<>(pn,total);
        Page<RepairDto> pageInfoDto=new Page<>(pn,total);
        LambdaQueryWrapper<Repair> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(type!=null,Repair::getType,type);
        page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<Repair> records = pageInfo.getRecords();
        List<RepairDto> repairDtoList = records.stream().map((items) -> {
            RepairDto repairDto = new RepairDto();
            BeanUtils.copyProperties(items, repairDto);

            String publishTime=items.getPublishTime().toString();
            publishTime=publishTime.replace("T"," ");

            repairDto.setPublishTimeStr(publishTime);

            Integer userId = items.getPublisher();
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(userId!=null, User::getUserId,userId);
            User user = userService.getOne(queryWrapper,false);
            if (user != null) {
                repairDto.setUserName(user.getUsername());
            }
            return repairDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(repairDtoList);

        return R.success(pageInfoDto);
    }

    @Override
    public R getUserRepair(String phone) {

        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();

        final LambdaQueryWrapper<Repair> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(userId!=null,Repair::getPublisher,userId);
        final List<Repair> repairList = list(wrapper1);
        return R.success(repairList);

    }

    @Override
    public R saveRepair(String phone, String test) {

        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();
        final Repair repair = new Repair();
        repair.setText(test);
        repair.setPublisher(userId);
        repair.setPublishTime(LocalDateTime.now());
        repair.setType(0);

        final boolean isSuccess = save(repair);
        if (!isSuccess) {
            return R.error("保存失败！");
        }
        return R.success("保存成功");
    }
}
