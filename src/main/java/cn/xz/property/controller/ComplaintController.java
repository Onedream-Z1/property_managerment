package cn.xz.property.controller;

import cn.xz.property.Service.ComplaintService;
import cn.xz.property.Service.ComplaintService;
import cn.xz.property.entity.Complaint;
import cn.xz.property.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public R pageList(Integer pn,Integer total,Integer status){
        return complaintService.pageListInfo(pn,total,status);
    }

    @PutMapping("/update")
    public R update(@RequestBody Complaint complaint){
        boolean isSuccess = complaintService.updateById(complaint);
        if (!isSuccess) {
            return R.error("修改失败！");
        }
        return R.success("修改成功！");
    }

    @DeleteMapping("/{complaintId}")
    public R delete(@PathVariable("complaintId") Integer complaintId){
        boolean isSuccess = complaintService.removeById(complaintId);
        if (!isSuccess) {
            return R.error("删除失败！");
        }else{
            return R.success("删除成功！");
        }
    }


    @GetMapping("/getOne/{phone}")
    public R getUserComplaint(@PathVariable("phone") String phone){
        return complaintService.getUserComplaint(phone);
    }

    @PostMapping("/save/{phone}")
    public R saveComplaint(@PathVariable("phone") String phone,@RequestBody String test){
        return complaintService.saveComplaint(phone,test);
    }


}
