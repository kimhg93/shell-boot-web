package com.boot.shell.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Method;

/**
 * quartz 를 쓸지.. 이걸쓸지.........
 * 실행할 메소드를 DB에 저장해 두고
 * DB 값을 불러와 스케줄을 등록 함
 */
@Component
public class CustomScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPoolTaskScheduler scheduler;

    @PostConstruct
    public void init() {
        startScheduler();
    }

    @PreDestroy
    public void destroy() {
        stopScheduler();
    }

    /**
     * http://www.cronmaker.com/
     * 크론을 직접입력할지... 입력 범위를지정해서 만들어 줄지...????????????????
     * ex)
     * 매달 10일 15시 10분 실행 >> 0 10 15 10 1/1 ? *
     * 매주 화 목 15시 20분 실행 >> 0 20 15 ? * TUE,THU *
     * 매일 15시 30분 실행 >> 0 30 15 1/1 * ? *
     */
    private Trigger getTrigger(String cron) {
        return new CronTrigger(cron);
    }

    private Runnable runnable(CustomScheduleVo job){
        //return new Thread(() -> callJob(job)); 스레드 생성할지?
        return () -> callJob(job);
    }

    public void startScheduler(){
        JobList jobList = new JobList();

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.initialize();

        for(CustomScheduleVo job : jobList.getJobList()){
            scheduler.schedule(runnable(job), getTrigger(job.getCron()));
            logger.info(job.toString());
        }
    }

    public void stopScheduler() {
        scheduler.shutdown();
    }

    public void callJob(CustomScheduleVo job) {
        try {
            Class<?> strClass = Class.forName(job.getClassName());
            Object obj = strClass.newInstance();
            Method m = strClass.getDeclaredMethod(job.getMethodName(), Object.class);
            m.invoke(obj, job);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
