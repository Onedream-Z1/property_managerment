package cn.xz.property.Service;

import cn.xz.property.entity.Parking;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ParkingService extends IService<Parking> {
    R palistInfo(Integer pn, Integer total, Integer status);
}
