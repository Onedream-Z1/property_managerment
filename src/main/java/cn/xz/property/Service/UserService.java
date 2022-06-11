package cn.xz.property.Service;

import cn.xz.property.entity.User;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    R pageListInfo(Integer pn, Integer total, String name);

    R updatePartAndRoom(Integer userId, String flag);

    R allocationRoom(Integer userId, Integer roodId);

    R allocationPart(Integer userId, Integer roodId);

    R findOne(Integer userId);

    R cofirmPayment(Integer userId);

    R getCode(String phone);

    R userLogin(String phone, String code);
}
