package cn.xz.property.controller;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.AdminService;
import cn.xz.property.entity.Admin;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Slf4j
public class AdminController {


    @Autowired
    private AdminService adminService;

    @PostMapping("/{phone}/{password}/{code}")
    public R adminLogin(@PathVariable("phone") String phone,@PathVariable("password") String password,@PathVariable("code") String code, HttpServletRequest request){
        return adminService.adminLogin(phone,password,code,request);
    }

    @GetMapping("/getCode")
    public R<String> getCode(){
        return adminService.getCode();
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request){

        final HttpSession session = request.getSession();
        session.removeAttribute("admin");
        return R.success("退出成功！");
    }

    @PostMapping("/changePass/{phone}/{oldPass}/{newPass}")
    public R changePass(@PathVariable String phone,@PathVariable String oldPass,@PathVariable String newPass){
        log.info("map={},{},{}",phone,oldPass,newPass);
        return adminService.changePassword(phone,oldPass,newPass);
    }

    @GetMapping("/getAll")
    public R getAll(){
        List<Admin> admins = adminService.list();
        return R.success(admins);
    }

    @GetMapping("/getAdmin/{phone}")
    public R getAdmin(@PathVariable("phone") String phone){
        final LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone),Admin::getPhone,phone);
        final Admin admin = adminService.getOne(wrapper, false);
        return R.success(admin);
    }

    @PutMapping("/update")
    public R update(@RequestBody Admin admin){
        final boolean isSuccess = adminService.updateById(admin);
        if (!isSuccess){
            return R.error("更新失败！");
        }
        return R.success("更新成功！");
    }

    @PostMapping("/addAdmin")
    public R addAdmin(@RequestBody Admin admin){

        try {
            final String phone = admin.getPhone();
            final LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();

            final boolean isSuccess = adminService.save(admin);
            if (!isSuccess) {
                return R.error("新增失败！");
            }
            return R.success("新增成功！");
        }catch (Exception e){
            return R.error("服务器内部异常！");
        }



    }
}
