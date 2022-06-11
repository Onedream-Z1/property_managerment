package cn.xz.property.dto;

import cn.xz.property.entity.Repair;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RepairDto extends Repair {
    private String userName;
    private String publishTimeStr;
}
