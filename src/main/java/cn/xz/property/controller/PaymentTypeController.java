package cn.xz.property.controller;

import cn.xz.property.Service.PaymentTypeService;
import cn.xz.property.entity.Building;
import cn.xz.property.entity.PaymentType;
import cn.xz.property.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-type")
@Slf4j
public class PaymentTypeController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @GetMapping
    public R pageList(Integer pn,Integer total,String name){
        return paymentTypeService.pageList(pn,total,name);
    }

    @PostMapping("/save")
    public R savePaymentType(@RequestBody PaymentType paymentType){
        return paymentTypeService.saveBuilding(paymentType);
    }

    @PutMapping("/update")
    public R updatePaymentType(@RequestBody PaymentType paymentType){
        return paymentTypeService.updateBuilding(paymentType);
    }

    @GetMapping("/getAll")
    public R getAll(){
        List<PaymentType> paymentTypeList = paymentTypeService.list();
        return R.success(paymentTypeList);
    }

    @DeleteMapping("/{paymentTypeId}")
    public R delete(@PathVariable("paymentTypeId") Integer paymentTypeId){
        boolean isSuccess = paymentTypeService.removeById(paymentTypeId);
        if(!isSuccess){
            R.error("删除失败！");
        }
        return R.success("删除成功！");
    }
    

}
