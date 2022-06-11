package cn.xz.property.Service.impl;

import cn.xz.property.Service.AdminService;
import cn.xz.property.entity.Admin;
import cn.xz.property.mapper.AdminMapper;
import cn.xz.property.util.R;
import cn.xz.property.util.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private final String keys="login:code:";

    @Override
    public R adminLogin(String phone, String password,String code, HttpServletRequest request) {
        String redisCode = stringRedisTemplate.opsForValue().get(keys);
        if( redisCode == null || !redisCode.equals(code)){
            return R.error("验证码错误");
        }
        LambdaQueryWrapper<Admin> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(phone!=null,Admin::getPhone,phone);
        Admin admin = getOne(lambdaQueryWrapper);
        if (admin==null || !admin.getPassword().equals(password)){
            return R.error("手机号或者密码错误！");
        }
        HttpSession session = request.getSession();
        session.setAttribute("admin",phone);
        stringRedisTemplate.delete(keys);
        return R.success(admin);
    }

    @Override
    public R<String> getCode( ) {
        Random random=new Random();
        StringBuilder codeStr= new StringBuilder();
        for (int i=0;i<6;i++){
            codeStr.append(random.nextInt(6));
        }
        log.info("codeStr,{}",codeStr);
        try {
            stringRedisTemplate.opsForValue().set(keys, codeStr.toString(),60L,TimeUnit.SECONDS);
        }catch (Exception e){
            return R.error("验证码发送失败,接口异常!");
        }
        return R.success("验证码发送成功,60秒有效!");
    }

    @Override
    public R changePassword(String phone, String oldPass, String newPass) {

        final LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(phone!=null,Admin::getPhone,phone);
        Admin admin = getOne(lambdaQueryWrapper);
        if(!admin.getPassword().equals(oldPass)){
            return R.error("旧密码错误，请重新输入!");
        }
        admin.setPassword(newPass);
        update(admin,null);
        return R.success("修改密码成功!");
    }
}
