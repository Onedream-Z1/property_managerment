package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Unit {
    @TableId("unit_id")
    private Integer unitId;
    @NotBlank(message = "单元名不能为空")
    private String name;
    @NotBlank(message = "隶书楼栋不能为空")
    private Integer belongBuilding;
}
