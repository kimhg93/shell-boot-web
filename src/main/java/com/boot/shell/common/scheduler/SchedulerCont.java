package com.boot.shell.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/scheduler/*")
public class SchedulerCont {

    private final CustomScheduler customScheduler;

    @GetMapping(value="/run")
    public void run() {
        customScheduler.startScheduler();
    }

    @GetMapping(value="/stop")
    public void stop(){
        customScheduler.destroy();
    }

    @GetMapping(value="/status")
    public Boolean status(){
        return customScheduler.isRunning();
    }

    @GetMapping(value = "/class") // 클래스 유효성 검사용
    public String checkClass(String className) {
        StringBuilder sb = new StringBuilder();
        try {
            Method[] arr = Class.forName(className).getDeclaredMethods();
            for (Method method : arr) {
                sb.append(method.getName())
                        .append(", params : ")
                        .append(Arrays.toString(method.getParameters()))
                        .append("<br/>");
            }
            return sb.toString();
        } catch (ClassNotFoundException e){
            return "Class Not Found " + className;
        }
    }



}
