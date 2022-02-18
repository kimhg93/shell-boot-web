package com.boot.shell.web.test;

import com.boot.shell.common.mail.Mail;
import com.boot.shell.common.mail.MailService;
import com.boot.shell.scheduler.CustomScheduler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CustomScheduler customScheduler;


    private final MailService mailService;

    @RequestMapping(value="/run", method = RequestMethod.GET)
    public void run(int a) {
        customScheduler.startScheduler(a);
    }

    @RequestMapping(value="/stop", method = RequestMethod.GET)
    public void stop(){
        customScheduler.destroy();
    }

    @RequestMapping(value="/mail",method = RequestMethod.GET)
    public void execMail() {
        Mail mail = new Mail();
        mail.setMailTo("kimhg93@gmail.com");
        mail.setMailSubject("test");
        mail.setMailContent("!111123345");
        mailService.mailSend(mail);
    }

}
