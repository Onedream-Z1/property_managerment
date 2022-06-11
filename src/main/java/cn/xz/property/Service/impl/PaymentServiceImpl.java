package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.PaymentService;
import cn.xz.property.Service.PaymentTypeService;
import cn.xz.property.Service.UserService;
import cn.xz.property.dto.PaymentDto;
import cn.xz.property.entity.*;
import cn.xz.property.entity.Payment;
import cn.xz.property.mapper.PaymentMapper;
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
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTypeService paymentTypeService;

    @Override
    public R pageListInfo(Integer pn, Integer total, Integer status) {

        Page<Payment> pageInfo=new Page<>(pn,total);
        Page<PaymentDto> pageInfoDto=new Page<>(pn,total);
        page(pageInfo,null);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<Payment> records = pageInfo.getRecords();
        List<PaymentDto> paymentDtoList = records.stream().map((items) -> {
            PaymentDto paymentDto = new PaymentDto();
            BeanUtils.copyProperties(items, paymentDto);

            Integer userId = items.getUserId();
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(userId!=null, User::getUserId,userId);
            User user = userService.getOne(queryWrapper,false);
            if (user != null) {
                paymentDto.setUserName(user.getUsername());
            }

            Integer paymentId = items.getPaymentType();
            LambdaQueryWrapper<PaymentType> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(paymentId!=null,PaymentType::getPaymentTypeId,paymentId);
            PaymentType paymentType = paymentTypeService.getOne(queryWrapper1, false);
            if(paymentType!=null){
                paymentDto.setPaymentTypeName(paymentType.getName());
            }

            return paymentDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(paymentDtoList);

        return R.success(pageInfoDto);

    }

    @Override
    public R getUserPayment(String phone) {
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();

        final LambdaQueryWrapper<Payment> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(userId!=null,Payment::getUserId,userId);
        final List<Payment> paymentList = list(wrapper1);

        List<PaymentDto> paymentDtoList = paymentList.stream().map((items) -> {
            PaymentDto paymentDto = new PaymentDto();
            BeanUtils.copyProperties(items, paymentDto);

            Integer paymentId = items.getPaymentType();
            LambdaQueryWrapper<PaymentType> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(paymentId!=null,PaymentType::getPaymentTypeId,paymentId);
            PaymentType paymentType = paymentTypeService.getOne(queryWrapper1, false);
            if(paymentType!=null){
                paymentDto.setPaymentTypeName(paymentType.getName());
            }

            return paymentDto;
        }).collect(Collectors.toList());

        
        return R.success(paymentDtoList);
    }

    @Override
    public R savePayment(String phone, String input, String value) {

        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();
        final Payment payment = new Payment();

        payment.setPaymentType(Integer.parseInt(input));
        payment.setPaymentMoney(Float.parseFloat(value));
        payment.setUserId(userId);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(0);

        final boolean isSuccess = save(payment);
        if (!isSuccess) {
            return R.error("保存失败！");
        }
        return R.success("保存成功");
        
        
    }
}
