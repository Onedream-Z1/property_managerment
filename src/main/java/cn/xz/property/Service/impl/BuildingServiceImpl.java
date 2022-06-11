package cn.xz.property.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xz.property.Service.BuildingService;
import cn.xz.property.entity.Building;
import cn.xz.property.mapper.BuildingMapper;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {

    @Override
    public R pageListInfo(int pn, int total,String name) {
        Page<Building> pageInfo=new Page<>(pn,total);
        LambdaQueryWrapper<Building> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name),Building::getName,name);
        page(pageInfo,wrapper);
        return R.success(pageInfo);
    }

    @Override
    public R updateBuilding(Building building) {

        boolean isSuccess = updateById(building);
        if(!isSuccess){
            return R.error("修改失败！");
        }
        return R.success("修改成功！");
    }

    @Override
    public R saveBuilding(Building building) {

         boolean isSuccess = save(building);
        if(!isSuccess){
            return R.error("新增失败！");
        }
        return R.success("新增成功！");
    }
}
