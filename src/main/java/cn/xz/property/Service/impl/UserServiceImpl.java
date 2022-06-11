package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.*;
import cn.xz.property.dto.NoticeDto;
import cn.xz.property.dto.UserDto;
import cn.xz.property.entity.*;
import cn.xz.property.mapper.UserMapper;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private ParkingService parkingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String keys="mobile:code:";

    @Override
    public R pageListInfo(Integer pn, Integer total, String name) {

        Page<User> pageInfo=new Page<>(pn,total);
        Page<UserDto> pageInfoDto=new Page<>(pn,total);
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name),User::getUsername,name);
        page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");
        List<User> records = pageInfo.getRecords();

        List<UserDto> userDtoList = records.stream().map((items) -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(items, userDto);

            Integer partId = items.getParting();
            Integer roomId = items.getRoom();
            if(partId!=null){
                final LambdaQueryWrapper<Parking> parkWrapper = new LambdaQueryWrapper<>();
                parkWrapper.eq(partId!=null,Parking::getParkId,partId);
                final Parking parking = parkingService.getOne(parkWrapper, false);
                if (parking != null) {
                    userDto.setPartName(parking.getName());
                }
            }

            if(roomId!=null){
                final LambdaQueryWrapper<Room> roomWrapper = new LambdaQueryWrapper<>();
                roomWrapper.eq(roomId!=null,Room::getRoomId,roomId);
                final Room room = roomService.getOne(roomWrapper, false);
                Integer belongUnit=null;
                if(room!=null){
                    belongUnit = room.getBelongUnit();
                }
                final LambdaQueryWrapper<Unit> unitWrapper = new LambdaQueryWrapper<>();
                unitWrapper.eq(belongUnit!=null,Unit::getUnitId,belongUnit);
                Unit unit = unitService.getOne(unitWrapper, false);

                if(room!=null){
                    userDto.setRoomName(unit.getName()+room.getName());
                }
            }

            return userDto;

        }).collect(Collectors.toList());

        pageInfoDto.setRecords(userDtoList);
        return R.success(pageInfoDto);
    }

    @Override
    public R updatePartAndRoom(Integer userId, String flag) {
        User user = getById(userId);
        if(flag.equals("park")){
            if(user.getParting()==0 || user.getParting() == null){
                return R.error("该用户没有车位，无法进行操作！");
            }
            user.setParting(0);

            final LambdaQueryWrapper<Parking> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(userId>0,Parking::getUserId,userId);
            final Parking parking = parkingService.getOne(wrapper, false);
            parking.setStatus(0);
            parking.setUserId(0);
            parkingService.updateById(parking);


        }else{
            if(user.getRoom()==0 || user.getRoom() == null){
                return R.error("该用户没有房屋，无法进行操作！");
            }
            user.setRoom(0);

            final LambdaQueryWrapper<Room> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(userId>0,Room::getUserId,userId);
            final Room room = roomService.getOne(wrapper2, false);
            room.setStatus(0);
            room.setUserId(0);
            roomService.updateById(room);
        }

        final boolean isSuccess = updateById(user);
        if (!isSuccess) {
            return R.error("操作失败！");
        }
        return R.success("操作成功！");
    }

    @Override
    public R allocationRoom(Integer userId, Integer roodId) {

        final User user = getById(userId);
        user.setRoom(roodId);
        final boolean isSuccess1 = updateById(user);

        final Room room = roomService.getById(roodId);
        room.setUserId(userId);
        room.setStatus(1);
        final boolean isSuccess2 = roomService.updateById(room);

        if(!isSuccess1 && !isSuccess2){
            return R.error("房屋分配失败！");
        }
        return R.success("房屋分配成功！");

    }

    @Override
    public R allocationPart(Integer userId, Integer partId) {

        final User user = getById(userId);
        user.setParting(partId);
        final boolean isSuccess1 = updateById(user);

        final Parking parking = parkingService.getById(partId);
        parking.setStatus(1);
        parking.setUserId(userId);
        final boolean isSuccess2 = parkingService.updateById(parking);

        if(!isSuccess1 && !isSuccess2){
            return R.error("车位分配失败！");
        }
        return R.success("车位分配成功！");
    }

    //查询支付详情
    @Override
    public R findOne(Integer userId) {

        final LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId!=null,Payment::getUserId,userId);
        final Payment payment = paymentService.getOne(wrapper, false);
        return R.success(payment);
    }

    @Override
    public R cofirmPayment(Integer userId) {

        final LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId!=null,Payment::getUserId,userId);
        final Payment payment = paymentService.getOne(wrapper, false);
        payment.setStatus(0);
        final boolean isSuccess = paymentService.updateById(payment);
        if (!isSuccess) {
            return R.error("确认缴费失败！");
        }
        return R.success("确认缴费成功！");

    }

    @Override
    public R getCode(String phone) {

        Random random=new Random();
        StringBuilder codeStr= new StringBuilder();
        for (int i=0;i<6;i++){
            codeStr.append(random.nextInt(6));
        }
        log.info("codeStr,{}",codeStr);

        try {
            stringRedisTemplate.opsForValue().set(keys+phone, codeStr.toString(),60L, TimeUnit.SECONDS);
        }catch (Exception e){
            return R.error("验证码发送失败,接口异常!");
        }
        return R.success("验证码发送成功,60秒有效!");

    }

    @Override
    public R userLogin(String phone, String code) {

        String redisCode = stringRedisTemplate.opsForValue().get(keys+phone);
        if( redisCode == null || !redisCode.equals(code)){
            return R.error("验证码错误");
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(phone!=null,User::getPhone,phone);
        User user = getOne(lambdaQueryWrapper);
        if (user==null ){
            return R.error("手机号错误！");
        }

        stringRedisTemplate.delete(keys);
        return R.success(user);
    }
}
