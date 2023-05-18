package com.boot.shell.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPoolTaskScheduler scheduler;
    private final SchedulerService schedulerService;

    @PostConstruct // Bean 초기화 직후 실행
    public void init() {
        //startScheduler();
    }

    @PreDestroy
    public void destroy() {
        stopScheduler();
    }

    /**
     * cron 표현식으로 trigger 객체 생성
     * @param cron
     * @return Trigger
     */
    private Trigger getTrigger(String cron) {
        if(cronValidate(cron)) return new CronTrigger(cron);
        else {
            stopScheduler();
            return null;
        }
    }

    /**
     * cron 표현식 검증
     * @param cronStr
     * @return boolean
     */
    private boolean cronValidate(String cronStr) {
        return CronExpression.isValidExpression(cronStr);
    }


    /**
     * Runnable 객체 생성 후 Runnble.run 실행
     * @param job
     * @return Runnable
     *
     * private Runnable runnable(CustomScheduleVo job) {
     *     Runnable rn = new Runnable() {
     *         @Override
     *         public void run() {
     *             callJob(job);
     *         }
     *     }
     *     return rn;
     * }
     */
    private Runnable runnable(CustomScheduleVo job) {
        return () -> callJob(job);
    }

    /**
     * 스케줄러 실행
     */
    public void startScheduler(){ // 스케줄러 실행, 항상 새로 스케줄링 함
        //logger.info("Start Scheduler !!");

        if(scheduler != null && !scheduler.getScheduledExecutor().isShutdown()){
            stopScheduler(); // 실행중인 스케줄이 있으면 종료
        }

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4); // 4 쓰레드 설정 부족시 8 까지 올릴예정
        scheduler.initialize();

        addSchedule(); // 스케줄 등록
    }

    /**
     * 스케줄 등록
     */
    private void addSchedule(){
        List<CustomScheduleVo> jobList = schedulerService.selectScheduleList();

        if(null == jobList) {
            stopScheduler();
            return;
        }

        for(CustomScheduleVo job : jobList){ // job 목록을 가져와서 스케줄 등록
            scheduler.schedule(runnable(job), getTrigger(job.getCron()));
            logger.info(job.toString());
        }
    }

    /**
     * 스케줄러 중단
     */
    public void stopScheduler() { // 스케줄러 종료
        scheduler.shutdown();
        scheduler.destroy();
        scheduler = null;
    }

    /**
     * job 실행
     * @param job
     */
    private void callJob(CustomScheduleVo job) {
        Class strClass = null;
        Object obj = null;
        Method m = null;
        try {
            strClass = Class.forName(job.getClassNm()); // 실행 클래스
            obj = strClass.getDeclaredConstructor().newInstance(); // 클래스명으로 인스턴스 생성
            m = strClass.getDeclaredMethod(job.getMethodNm(), Object.class); // 실행 메소드

            setJobStatus(m.getName() + ": running", LocalDateTime.now()); // 실행 상태값 저장
            m.invoke(obj, job); // job 실행, 생성된 인스턴스와 파라미터로 쓸 job 객체 넘겨줌
            setJobStatus(m.getName() + ": finished", LocalDateTime.now()); // 종료 상태값 저장

        } catch (ClassNotFoundException e){

            //logger.error("Scheduler Class Not Found");
            e.printStackTrace();
            if(scheduler != null) stopScheduler();

        } catch (InvocationTargetException e) {
            StringBuilder sb = new StringBuilder();
            Throwable methodException = e.getCause(); // 실행메소드
            StackTraceElement[] stackTrace = methodException.getStackTrace(); // stackTrace 배열로 받아옴

            if(methodException.getMessage() != null) sb.append(methodException.getMessage()); // exception 메시지
            sb.append(methodException.getClass().getName()).append("\n"); // exception 객체 종류
            for (StackTraceElement msg : stackTrace) {
                if(sb.length() > 2000) break; // stackTrace가 2000자 까지만 저장
                sb.append(msg).append("\n");
            }

            saveErrorLog(job, sb.toString()); // 에러 로그 저장

        } catch (Exception e){
            e.printStackTrace();
            if(scheduler != null) stopScheduler();
        }
    }

    /**
     * 스케줄러 상태 확인
     * @return boolean
     */
    public boolean isRunning(){
        return scheduler != null && !scheduler.getScheduledExecutor().isShutdown();
    }

    /**
     * 실행된 job의 실행 상태 저장
     * @param status
     * @param time
     */
    public void setJobStatus(String status, LocalDateTime time){
        System.err.println(status);
        System.err.println(time);
        Timestamp timestamp = Timestamp.valueOf(time);
        // save action
    }

    /**
     * job 실행 중 에러 발생시 로그 저장
     * @param job
     * @param msg
     */
    public void saveErrorLog(CustomScheduleVo job, String msg){
        setJobStatus("ERROR", LocalDateTime.now());
        System.out.println(msg);
        String method = job.getClassNm()+"."+job.getMethodNm();
        // save action
    }
}
