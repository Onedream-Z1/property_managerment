package cn.xz.property.controller;

import cn.xz.property.Service.PaymentService;
import cn.xz.property.entity.Payment;
import cn.xz.property.util.R;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public R pageList(Integer pn,Integer total,Integer status){
        return paymentService.pageListInfo(pn,total,status);
    }

    @PutMapping("/update")
    public R update(@RequestBody Payment payment){
        payment.setStatus(0);
        final boolean isSuccess = paymentService.updateById(payment);
        if (!isSuccess) {
            return R.error("确认缴费失败，请重试！");
        }
        return R.success("确认缴费成功,请勿重试！");
    }

    @GetMapping("/getOne/{phone}")
    public R getUserPayment(@PathVariable("phone") String phone){
        return paymentService.getUserPayment(phone);
    }

    @PostMapping("/save/{phone}/{input}/{value}")
    public R savePayment(@PathVariable("phone") String phone, @PathVariable("input") String input,@PathVariable("value") String value){
        return paymentService.savePayment(phone,input,value);
    }



}
