package cn.xz.property.controller;

import cn.xz.property.Service.NoticeService;
import cn.xz.property.entity.Notice;
import cn.xz.property.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public R pageList(Integer pn,Integer total,String name){
        return noticeService.pageListInfo(pn,total,name);
    }

    @PostMapping("/save")
    public R save(@RequestBody Notice notice){
        notice.setPublishTime(LocalDateTime.now());
        notice.setMenderTime(LocalDateTime.now());
        boolean isSuccess = noticeService.save(notice);
        if (!isSuccess) {
            return R.error("添加失败！");
        }
        return R.success("添加成功！");
    }

    @PutMapping("/update")
    public R update(@RequestBody Notice notice){
        notice.setMenderTime(LocalDateTime.now());
        boolean isSuccess = noticeService.updateById(notice);
        if (!isSuccess) {
            return R.error("修改失败！");
        }
        return R.success("修改成功");
    }

    @GetMapping("/notice?")
    public R getNotices(){
        final List<Notice> notices = noticeService.list();
        return R.success(notices);
    }
}
