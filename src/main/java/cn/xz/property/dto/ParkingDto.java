package cn.xz.property.dto;

import cn.xz.property.entity.Parking;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingDto extends Parking {
    private String username;
    private String phone;
    private int sex;
    private String address;
}
