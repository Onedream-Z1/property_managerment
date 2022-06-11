package cn.xz.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "notice")
public class Notice {
    @TableId(value = "notice_id",type = IdType.AUTO)
    private Integer noticeId;
    private String subject;
    private String text;
    private Integer publisher;
    private LocalDateTime publishTime;
    private Integer mender;
    private LocalDateTime menderTime;
    private Integer type;
}
