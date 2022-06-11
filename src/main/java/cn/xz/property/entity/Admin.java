package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin")
public class Admin {
    @TableId(value = "admin_id",type = IdType.AUTO)
    private Integer adminId;
    private String username;
    private String password;
    private String phone;
    private int sex;
    private String email;
    private String description;
    private String address;
    private String tag;

}
