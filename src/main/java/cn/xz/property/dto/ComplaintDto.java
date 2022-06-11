package cn.xz.property.dto;

import cn.xz.property.entity.Complaint;
import cn.xz.property.entity.Repair;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ComplaintDto extends Complaint {
    private String userName;
    private String publishTimeStr;
}
