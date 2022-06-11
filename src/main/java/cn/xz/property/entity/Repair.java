package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Repair {
    @TableId(value = "repair_id",type = IdType.AUTO)
    private Integer repairId;
    private String text;
    private Integer publisher;
    private LocalDateTime publishTime;
    private String result;
    private Integer type;
}
