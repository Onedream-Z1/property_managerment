package cn.xz.property.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Statistic {
    private Long userNum;
    private Long noticeNum;
    private Long complaintNum;
    private Long repairNum;
    private Map<String,Long> room;
    private Map<String,Long> park;
    private Map<String,Long> payment;

}
