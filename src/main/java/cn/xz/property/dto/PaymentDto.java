package cn.xz.property.dto;

import cn.xz.property.entity.Payment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentDto extends Payment {
    private String userName;
    private String paymentTypeName;
}
