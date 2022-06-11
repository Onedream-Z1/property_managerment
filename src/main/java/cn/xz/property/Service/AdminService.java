package cn.xz.property.Service;

import cn.xz.property.entity.Admin;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface AdminService extends IService<Admin> {
    R adminLogin(String phone,String password, String code, HttpServletRequest request);

    R<String> getCode();

    R changePassword(String phone, String oldPass, String newPass);
}
