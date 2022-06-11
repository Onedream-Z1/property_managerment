package cn.xz.property.Service;

import cn.xz.property.entity.Repair;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RepairService extends IService<Repair> {
    R pageListInfo(Integer pn, Integer total, Integer status);

    R getUserRepair(String phone);

    R saveRepair(String phone, String test);
}
