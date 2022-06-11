package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.PaymentTypeService;
import cn.xz.property.entity.Building;
import cn.xz.property.entity.PaymentType;
import cn.xz.property.mapper.PaymentTypeMapper;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType> implements PaymentTypeService {
    @Override
    public R pageList(Integer pn, Integer total, String name) {
        Page<PaymentType> pageInfo=new Page<>(pn,total);
        LambdaQueryWrapper<PaymentType> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name),PaymentType::getName,name);
        page(pageInfo,wrapper);
        return R.success(pageInfo);
    }

    @Override
    public R saveBuilding(PaymentType paymentType) {
        final boolean isSuccess = save(paymentType);
        if (!isSuccess) {
            return R.error("保存失败！");
        }
        return R.success("保存成功");
    }

    @Override
    public R updateBuilding(PaymentType paymentType) {
        final boolean isSuccess = updateById(paymentType);
        if (!isSuccess) {
            return R.error("修改失败！");
        }
        return R.success("修改成功");
    }

}
