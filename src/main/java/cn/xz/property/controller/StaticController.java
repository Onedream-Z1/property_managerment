package cn.xz.property.controller;

import cn.xz.property.Service.*;
import cn.xz.property.entity.Parking;
import cn.xz.property.entity.Payment;
import cn.xz.property.entity.Room;
import cn.xz.property.entity.Statistic;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/statistic")
@Slf4j
public class StaticController {

    @Autowired
    private UserService userService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ParkingService parkingService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public R getAllNum(){
        Statistic statistic = new Statistic();
        statistic.setUserNum(userService.count());
        statistic.setNoticeNum(noticeService.count());
        statistic.setComplaintNum(complaintService.count());
        statistic.setRepairNum(repairService.count());

        long roomTotal = roomService.count();
        final LambdaQueryWrapper<Room> roomWrapper = new LambdaQueryWrapper<>();
        roomWrapper.eq(Room::getStatus,0);
        final long roomFree = roomService.count(roomWrapper);
        final HashMap<String, Long> room = new HashMap<>();
        room.put("roomTotal",roomTotal);
        room.put("roomFree",roomFree);
        statistic.setRoom(room);

        long parkTotal = parkingService.count();
        final LambdaQueryWrapper<Parking> parkWrapper = new LambdaQueryWrapper<>();
        parkWrapper.eq(Parking::getStatus,0);
        final long parkFree = parkingService.count(parkWrapper);
        final HashMap<String, Long> park = new HashMap<>();
        park.put("parkTotal",parkTotal);
        park.put("parkFree",parkFree);
        statistic.setPark(park);

        long paymentTotal = paymentService.count();
        final LambdaQueryWrapper<Payment> paymentWrapper = new LambdaQueryWrapper<>();
        paymentWrapper.eq(Payment::getStatus,1);
        final long paymentFree = paymentService.count(paymentWrapper);
        final HashMap<String, Long> payment = new HashMap<>();
        payment.put("paymentTotal",paymentTotal);
        payment.put("paymentFree",paymentFree);
        statistic.setPayment(payment);

        return R.success(statistic);
    }





}
