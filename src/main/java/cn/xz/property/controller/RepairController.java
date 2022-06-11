package cn.xz.property.controller;

import cn.xz.property.Service.RepairService;
import cn.xz.property.entity.Repair;
import cn.xz.property.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @GetMapping
    public R pageList(Integer pn,Integer total,Integer status){
        return repairService.pageListInfo(pn,total,status);
    }

    @PutMapping("/update")
    public R update(@RequestBody Repair repair){
        boolean isSuccess = repairService.updateById(repair);
        if (!isSuccess) {
            return R.error("修改失败！");
        }
        return R.success("修改成功！");
    }

    @DeleteMapping("/{repairId}")
    public R delete(@PathVariable("repairId") Integer repairId){
        boolean isSuccess = repairService.removeById(repairId);
        if (!isSuccess) {
            return R.error("删除失败！");
        }else{
            return R.success("删除成功！");
        }
    }

    @GetMapping("/getOne/{phone}")
    public R getUserRepair(@PathVariable("phone") String phone){
        return repairService.getUserRepair(phone);
    }

    @PostMapping("/save/{phone}")
    public R saveRepair(@PathVariable("phone") String phone,@RequestBody String test){
        return repairService.saveRepair(phone,test);
    }

}
