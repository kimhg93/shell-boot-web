package com.boot.shell.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void testJobA(Object param){
        logger.info("######## > testJobA / " + param.toString()+" #### " +java.lang.Thread.activeCount());
    }
    public void testJobB(Object param){
        logger.info("######## > testJobB / " + param.toString()+" #### " +java.lang.Thread.activeCount());
    }
    public void testJobC(Object param){
        logger.info("######## > testJobC / " + param.toString()+" #### " +java.lang.Thread.activeCount());
    }
}
