package cn.xz.property.controller;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.UserService;
import cn.xz.property.entity.Unit;
import cn.xz.property.entity.User;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户类相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation("套餐新增")
    public R pageList(Integer pn,Integer total,String name){
        return userService.pageListInfo(pn,total,name);
    }

    @PostMapping("/save")
    public R save(@RequestBody User user){
        final boolean isSuccess = userService.save(user);
        if (!isSuccess) {
            return R.error("保存失败！");
        }
        return R.success("保存成功！");
    }

    @PutMapping("/update")
    public R update(@RequestBody User user){
        final boolean isSuccess = userService.updateById(user);
        if (!isSuccess) {
            return R.error("修改失败！");
        }
        return R.success("修改成功！");
    }

    @PutMapping("/{userId}/{flag}")
    public R updatePartAndRoom(@PathVariable("userId") Integer userId,@PathVariable("flag") String flag){
        return userService.updatePartAndRoom(userId,flag);
    }

    @GetMapping("/getAll")
    public R getAll(){
        final List<User> userList = userService.list();
        return R.success(userList);
    }

    @PutMapping("/allocationRoom/{userId}/{roodId}")
    public R allocationRoom(@PathVariable("userId") Integer userId, @PathVariable("roodId") Integer roodId){
        return userService.allocationRoom(userId,roodId);
    }

    @PutMapping("/allocationPark/{userId}/{partId}")
    public R allocationPart(@PathVariable("userId") Integer userId, @PathVariable("partId") Integer partId){
        return userService.allocationPart(userId,partId);
    }

    @GetMapping("/findOne/{userId}")
    public R findOne(@PathVariable("userId") Integer userId){
        return userService.findOne(userId);
    }

    @PutMapping("/payment/{userId}")
    public R cofirmPayment(@PathVariable("userId") Integer userId){
        return userService.cofirmPayment(userId);
    }

    @GetMapping("/getCode/{phone}")
    public R getCode(@PathVariable("phone") String phone){
        log.info("phone,{}",phone);
        return userService.getCode(phone);
    }

    @GetMapping("/userLogin")
    public R userLogin(String phone,String code){
        return userService.userLogin(phone,code);
    }

    @GetMapping("/getOne/{phone}")
    public R getOne(@PathVariable("phone") String phone){
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone), User::getPhone,phone);
        final User user = userService.getOne(wrapper, false);
        return R.success(user);
    }


}
