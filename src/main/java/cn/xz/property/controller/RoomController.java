package cn.xz.property.controller;

import cn.xz.property.Service.RoomService;
import cn.xz.property.entity.Room;
import cn.xz.property.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public R pageList(Integer pn,Integer total,Integer status){
        return roomService.pageListInfo(pn,total,status);
    }

    @PostMapping("/save")
    public R save(@RequestBody Room room){
        return roomService.saveRoom(room);
    }

    @PutMapping("/update")
    public R update(@RequestBody Room room){
        return roomService.updateRoom(room);
    }

    @DeleteMapping("/{roomId}")
    public R delete(@PathVariable("roomId") Integer roomId){
        boolean isSuccess = roomService.removeById(roomId);
        if(!isSuccess){
            return R.error("删除失败！");
        }
        return R.success("删除成功！");
    }

    @GetMapping("/getAll")
    public R getAll(){
        final List<Room> roomList = roomService.list();
        return R.success(roomList);
    }



}
