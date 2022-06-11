package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User{
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private String phone;
    private Integer sex;
    private Integer status;
    private String description;
    private String address;
    private Integer parting;
    private Integer room;
    private Integer payment;


}
