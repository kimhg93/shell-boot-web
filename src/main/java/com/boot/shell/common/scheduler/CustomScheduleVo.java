package com.boot.shell.common.scheduler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomScheduleVo {
    private String className;
    private String methodName;
    private String cron;
    private Object param;
}
