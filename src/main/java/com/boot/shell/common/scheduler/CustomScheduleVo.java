package com.boot.shell.common.scheduler;

import com.boot.shell.common.object.MapUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class CustomScheduleVo {

    private int scheduleId;
    private int jobId;
    private int appId;

    private String classNm;
    private String methodNm;
    private String cron;
    private String appNm;
    private String status;
    private String scheduleNm;

    private Object parameter;

    private Boolean isRunning;

    public void setParameter(String str) {
        Map<String, Object> map = new MapUtil().jsonToMap(str);
        if(null != map) this.parameter = map;
        else this.parameter = str;
    }
}