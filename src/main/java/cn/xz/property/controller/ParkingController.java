package cn.xz.property.controller;

import cn.xz.property.Service.ParkingService;
import cn.xz.property.entity.Parking;
import cn.xz.property.util.R;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Slf4j
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping
    public R pageList(Integer pn,Integer total,Integer status){
        return parkingService.palistInfo(pn,total,status);
    }

    @PostMapping("/save")
    public R save(@RequestBody Parking parking){
        final boolean isSuccess = parkingService.save(parking);
        if (!isSuccess) {
            return R.error("保存失败！");
        }
        return R.success("保存成功！");
    }

    @PutMapping("/update")
    public R update(@RequestBody Parking parking){
        final boolean isSuccess = parkingService.updateById(parking);
        if (!isSuccess) {
            return R.error("修改失败！");
        }
        return R.success("修改成功！");
    }

    @DeleteMapping("/{parkId}")
    public R delete(@PathVariable("parkId") Integer parkId){
        final boolean isSuccess = parkingService.removeById(parkId);
        if (!isSuccess) {
            return R.error("删除失败！");
        }
        return R.success("删除成功");
    }

    @GetMapping("/getAll")
    public R getAll(){
        final List<Parking> parkingList = parkingService.list();
        return R.success(parkingList);
    }

}
