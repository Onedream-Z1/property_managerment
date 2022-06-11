package cn.xz.property.dto;

import cn.xz.property.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends User {
    private String partName;
    private String roomName;
}
