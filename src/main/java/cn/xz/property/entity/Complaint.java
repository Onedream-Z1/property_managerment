package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Complaint {
    @TableId(value = "complaint_id")
    private Integer complaintId;
    private String text;
    private Integer publisher;
    private LocalDateTime publishTime;
    private String result;
    private Integer type;
}
