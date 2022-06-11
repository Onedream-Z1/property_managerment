package cn.xz.property.Service;

import cn.xz.property.entity.Building;
import cn.xz.property.entity.PaymentType;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PaymentTypeService extends IService<PaymentType> {
    R pageList(Integer pn, Integer total, String name);

    R saveBuilding(PaymentType paymentType);

    R updateBuilding(PaymentType paymentType);
}
