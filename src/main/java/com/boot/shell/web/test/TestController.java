package com.boot.shell.web.test;

import com.boot.shell.common.scheduler.CustomScheduler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CustomScheduler customScheduler;

    @GetMapping(value="/run")
    public void run(int a) {
        customScheduler.startScheduler(a);
    }

    @GetMapping(value="/stop")
    public void stop(){
        customScheduler.destroy();
    }

    @GetMapping(value = "/test")
    public String test() {
        return "view";
    }

}
