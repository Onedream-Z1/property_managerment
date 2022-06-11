package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Parking {
    @TableId(value = "park_id",type = IdType.AUTO)
    private Integer parkId;
    private String name;
    private Integer status;
    private Integer type;
    private Integer userId;
}
