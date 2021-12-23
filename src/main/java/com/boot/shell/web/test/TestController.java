package com.boot.shell.web.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
        logger.error("##################### TEST");
        return "view";
    }
}
