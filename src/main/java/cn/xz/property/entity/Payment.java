package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {
    @TableId(value = "payment_id")
    private Integer paymentId;
    private Integer paymentType;
    private Integer userId;
    private float paymentMoney;
    private LocalDateTime paymentTime;
    private Integer status;
}
