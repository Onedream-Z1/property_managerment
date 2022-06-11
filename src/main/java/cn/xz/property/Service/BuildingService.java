package cn.xz.property.Service;

import cn.xz.property.entity.Building;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BuildingService extends IService<Building> {
    R pageListInfo(int pn, int total,String name);

    R updateBuilding(Building building);

    R saveBuilding(Building building);
}
