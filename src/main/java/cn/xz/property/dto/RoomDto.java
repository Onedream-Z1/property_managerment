package cn.xz.property.dto;

import cn.xz.property.entity.Room;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomDto extends Room {
    private String username;
    private String phone;
    private int sex;
    private String address;
    private String unitName;
}
