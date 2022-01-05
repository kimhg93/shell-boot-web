package com.boot.shell.scheduler;

import java.util.ArrayList;
import java.util.List;

// DB에서 뽑아올 데이터 임시 생성
public class JobList {

    public List<CustomScheduleVo> getJobList(){
        CustomScheduleVo customScheduleVoA = new CustomScheduleVo();
        CustomScheduleVo customScheduleVoB = new CustomScheduleVo();
        CustomScheduleVo customScheduleVoC = new CustomScheduleVo();

        customScheduleVoA.setClassName("com.boot.shell.scheduler.TestJob");
        customScheduleVoB.setClassName("com.boot.shell.scheduler.TestJob");
        customScheduleVoC.setClassName("com.boot.shell.scheduler.TestJob");

        customScheduleVoA.setMethodName("testJobA");
        customScheduleVoB.setMethodName("testJobB");
        customScheduleVoC.setMethodName("testJobC");

        customScheduleVoA.setCron("*/5 * * * * *");
        customScheduleVoB.setCron("*/8 * * * * *");
        customScheduleVoC.setCron("*/14 * * * * *");

        customScheduleVoA.setParam("AAAA");
        customScheduleVoB.setParam("BBBB");
        customScheduleVoC.setParam("CCCC");

        List<CustomScheduleVo> list = new ArrayList<>();
        list.add(customScheduleVoA);
        list.add(customScheduleVoB);
        list.add(customScheduleVoC);

        return list;
    }
}
