package cn.xz.property.Service;

import cn.xz.property.entity.Room;
import cn.xz.property.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoomService extends IService<Room> {
    R pageListInfo(Integer pn,Integer total,Integer status);

    R saveRoom(Room room);

    R updateRoom(Room room);
}
