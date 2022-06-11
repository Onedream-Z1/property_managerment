package cn.xz.property.Service;

import cn.xz.property.entity.Unit;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UnitService extends IService<Unit> {
    R pageListInfo(Integer pn, Integer total, String name);

    R saveUnitInfo(Unit unit);

    R updateUnitInfo(Unit unit);
}
