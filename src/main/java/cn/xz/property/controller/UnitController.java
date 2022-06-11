package cn.xz.property.controller;

import cn.xz.property.Service.UnitService;
import cn.xz.property.entity.Unit;
import cn.xz.property.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit")
@Slf4j
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping()
    public R pageList(Integer pn,Integer total,String name){
        return unitService.pageListInfo(pn,total,name);
    }

    @PostMapping("/save")
    public R saveUnit(@RequestBody Unit unit){
        return unitService.saveUnitInfo(unit);
    }

    @PutMapping("/update")
    public R updateUnit(@RequestBody Unit unit){
        return unitService.updateUnitInfo(unit);
    }

    @DeleteMapping("/{unitId}")
    public R delete(@PathVariable("unitId") Integer unitId){
        final boolean isSuccess = unitService.removeById(unitId);
        if (!isSuccess){
            return R.error("删除失败！");
        }
        return R.success("删除成功！");
    }

    @GetMapping("/getAll")
    public R getAll(){
        List<Unit> unitList = unitService.list();
        return R.success(unitList);
    }

}
