package cn.xz.property.controller;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.ComplaintService;
import cn.xz.property.Service.PaymentService;
import cn.xz.property.Service.RepairService;
import cn.xz.property.Service.UserService;
import cn.xz.property.entity.*;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userStatistic")
@Slf4j
public class UserStatisticController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;

    @GetMapping("/{phone}")
    public R getUserStatisticNum(@PathVariable("phone") String phone){
        final LambdaQueryWrapper<User > wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        final Integer userId = user.getUserId();

        final LambdaQueryWrapper<Complaint> wrapper1 = new LambdaQueryWrapper<>();
        final LambdaQueryWrapper<Repair> wrapper2 = new LambdaQueryWrapper<>();
        final LambdaQueryWrapper<Payment> wrapper3 = new LambdaQueryWrapper<>();
        wrapper1.eq(userId!=null,Complaint::getPublisher,userId);
        wrapper2.eq(userId!=null,Repair::getPublisher,userId);
        wrapper3.eq(userId!=null,Payment::getUserId,userId);

        Long complaintNum=complaintService.count(wrapper1);
        Long repairNum=repairService.count(wrapper2);
        Long paymentNum=paymentService.count(wrapper3);
        final UserStatistic userStatistic = new UserStatistic();
        userStatistic.setComplaintNum(complaintNum);
        userStatistic.setRepairNum(repairNum);
        userStatistic.setPaymentNum(paymentNum);

        return R.success(userStatistic);

    }

}
