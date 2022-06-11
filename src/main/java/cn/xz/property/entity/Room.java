package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class Room {
    @TableId(value = "room_id",type = IdType.AUTO)
    private  int roomId;
    @NotBlank(message = "房间名不能为空")
    private String name;
    private float area;
    private Integer status;
    @NotBlank(message = "所属单元不能为空")
    private Integer belongUnit;
    private Integer userId;
}
