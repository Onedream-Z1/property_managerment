package cn.xz.property.dto;

import cn.xz.property.entity.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeDto extends Notice {
    private String publisherName;
    private String menderName;
    private String publishTimeStr;
    private String menderTimeStr;
}
