package cn.xz.property.dto;

import cn.xz.property.entity.Unit;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnitDto extends Unit {
    private String BuildingName;
}
