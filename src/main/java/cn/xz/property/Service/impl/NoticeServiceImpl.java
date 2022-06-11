package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.AdminService;
import cn.xz.property.Service.NoticeService;
import cn.xz.property.dto.NoticeDto;
import cn.xz.property.dto.RoomDto;
import cn.xz.property.entity.*;
import cn.xz.property.mapper.NoticeMapper;
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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private AdminService adminService;

    @Override
    public R pageListInfo(Integer pn, Integer total, String name) {
        Page<Notice> pageInfo=new Page<>(pn,total);
        Page<NoticeDto> pageInfoDto=new Page<>(pn,total);
        LambdaQueryWrapper<Notice> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name),Notice::getSubject,name);
        page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<Notice> records = pageInfo.getRecords();
        List<NoticeDto> noticeDtoList = records.stream().map((items) -> {
            NoticeDto noticeDto = new NoticeDto();
            BeanUtils.copyProperties(items, noticeDto);

            String publishTime=items.getPublishTime().toString();
            publishTime=publishTime.replace("T"," ");
            String menderTime=items.getMenderTime().toString();
            menderTime=menderTime.replace("T"," ");
            Integer publisherId = items.getPublisher();
            Integer menderId = items.getMender();

            noticeDto.setPublishTimeStr(publishTime);
            noticeDto.setMenderTimeStr(menderTime);

            LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(publisherId!=null,Admin::getAdminId,publisherId);
            Admin publisherAdmin = adminService.getOne(queryWrapper, false);
            LambdaQueryWrapper<Admin> queryWrapper2=new LambdaQueryWrapper<>();
            queryWrapper2.eq(menderId!=null,Admin::getAdminId,menderId);
            Admin menderAdmin = adminService.getOne(queryWrapper2, false);

            if (publisherAdmin != null) {
                noticeDto.setPublisherName(publisherAdmin.getUsername());
            }
            if (menderAdmin!=null){
                noticeDto.setMenderName(menderAdmin.getUsername());
            }
            return noticeDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(noticeDtoList);
        return R.success(pageInfoDto);

    }
}
