package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.ComplaintService;
import cn.xz.property.Service.ComplaintService;
import cn.xz.property.Service.UserService;
import cn.xz.property.dto.ComplaintDto;
import cn.xz.property.dto.ComplaintDto;
import cn.xz.property.entity.*;
import cn.xz.property.entity.Complaint;
import cn.xz.property.entity.Complaint;
import cn.xz.property.mapper.ComplaintMapper;
import cn.xz.property.mapper.ComplaintMapper;
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
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

    @Autowired
    private UserService userService;

    @Override
    public R pageListInfo(Integer pn, Integer total, Integer type) {

        Page<Complaint> pageInfo=new Page<>(pn,total);
        Page<ComplaintDto> pageInfoDto=new Page<>(pn,total);
        LambdaQueryWrapper<Complaint> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(type!=null,Complaint::getType,type);
        page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<Complaint> records = pageInfo.getRecords();

        List<ComplaintDto> complaintDtoList = records.stream().map((items) -> {
            ComplaintDto complaintDto = new ComplaintDto();
            BeanUtils.copyProperties(items, complaintDto);

            String publishTime=items.getPublishTime().toString();
            publishTime=publishTime.replace("T"," ");

            complaintDto.setPublishTimeStr(publishTime);

            Integer userId = items.getPublisher();
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(userId!=null, User::getUserId,userId);
            User user = userService.getOne(queryWrapper,false);
            if (user != null) {
                complaintDto.setUserName(user.getUsername());
            }
            return complaintDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(complaintDtoList);

        return R.success(pageInfoDto);
    }

    @Override
    public R getUserComplaint(String phone) {
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();
        final LambdaQueryWrapper<Complaint> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(userId!=null,Complaint::getPublisher,userId);
        final List<Complaint> complaints = list(wrapper1);
        return R.success(complaints);
    }

    @Override
    public R saveComplaint(String phone, String test) {

        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();
        final Complaint complaint = new Complaint();
        complaint.setText(test);
        complaint.setPublisher(userId);
        complaint.setPublishTime(LocalDateTime.now());
        complaint.setType(0);

        final boolean isSuccess = save(complaint);
        if (!isSuccess) {
            return R.error("保存失败！");
        }
        return R.success("保存成功");
    }
}
