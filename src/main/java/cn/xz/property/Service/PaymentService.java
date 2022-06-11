package cn.xz.property.Service;

import cn.xz.property.entity.Payment;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PaymentService extends IService<Payment> {
    R pageListInfo(Integer pn, Integer total,Integer status);

    R getUserPayment(String phone);

    R savePayment(String phone, String input, String value);
}
