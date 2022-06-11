package cn.xz.property.controller;

import cn.xz.property.Service.BuildingService;
import cn.xz.property.entity.Building;
import cn.xz.property.util.R;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
@Slf4j
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping()
    public R pageList(Integer pn,Integer total,String name){
        return buildingService.pageListInfo(pn,total,name);
    }

    @PostMapping("/save")
    public R saveBuilding(@RequestBody Building building){
        return buildingService.saveBuilding(building);
    }

    @PutMapping("/update")
    public R updateBuilding(@RequestBody Building building){
        return buildingService.updateBuilding(building);
    }

    @GetMapping("/getAll")
    public R getAll(){
        List<Building> buildingList = buildingService.list();
        return R.success(buildingList);
    }

    @DeleteMapping("/{buildingId}")
    public R delete(@PathVariable("buildingId") Integer buildingId){
         boolean isSuccess = buildingService.removeById(buildingId);
         if(!isSuccess){
             R.error("删除失败！");
         }
         return R.success("删除成功！");
    }

}
