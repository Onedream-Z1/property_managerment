package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Building {
    @TableId("building_id")
    private Integer buildingId;
    @NotBlank(message = "楼栋名不能为空")
    private String name;
    @Min(1)
    @NotEmpty(message = "楼栋类型不能为空")
    private Integer type;
}
