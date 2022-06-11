package cn.xz.property.Service;

import cn.xz.property.entity.Notice;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeService extends IService<Notice> {
    R pageListInfo(Integer pn, Integer total, String name);
}
