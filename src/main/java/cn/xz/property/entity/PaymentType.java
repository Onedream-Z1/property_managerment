package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "payment_type")
public class PaymentType {
    @TableId(value = "payment_type_id",type = IdType.AUTO)
    private Integer paymentTypeId;
    private String name;
}
