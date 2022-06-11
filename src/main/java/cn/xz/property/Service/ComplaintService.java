package cn.xz.property.Service;

import cn.xz.property.entity.Complaint;
import cn.xz.property.entity.Repair;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ComplaintService extends IService<Complaint> {
    R pageListInfo(Integer pn, Integer total, Integer status);

    R getUserComplaint(String phone);

    R saveComplaint(String phone, String test);
}
