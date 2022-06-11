package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.BuildingService;
import cn.xz.property.Service.UnitService;
import cn.xz.property.dto.UnitDto;
import cn.xz.property.entity.Building;
import cn.xz.property.entity.Unit;
import cn.xz.property.mapper.UnitMapper;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {

    @Autowired
    private BuildingService buildingService;

    @Override
    public R pageListInfo(Integer pn, Integer total, String name) {
        Page<Unit> pageInfo=new Page<>(pn,total);
        Page<UnitDto> pageInfoDto=new Page<>(pn,total);

        LambdaQueryWrapper<Unit> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name),Unit::getName,name);
        page(pageInfo,wrapper);

        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");

         List<Unit> records = pageInfo.getRecords();
        List<UnitDto> unitDtos = records.stream().map((items) -> {
            UnitDto unitDto = new UnitDto();
            BeanUtils.copyProperties(items, unitDto);
            int buildingId = items.getBelongBuilding();
            Building building = buildingService.getById(buildingId);
            if (building != null) {
                unitDto.setBuildingName(building.getName());
            }
            return unitDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(unitDtos);

        return R.success(pageInfoDto);
    }

    @Override
    public R saveUnitInfo(Unit unit) {

        boolean isSuccess = save(unit);
        if(!isSuccess){
            return R.error("保存失败！");
        }
        return R.success("保存成功！");
    }

    @Override
    public R updateUnitInfo(Unit unit) {
        boolean isSuccess = updateById(unit);
        if(!isSuccess){
            return R.error("修改失败！");
        }
        return R.success("修改成功！");
    }
}
