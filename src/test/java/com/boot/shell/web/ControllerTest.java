package com.boot.shell.web;

import com.boot.shell.web.test.TestController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TestController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mok;

    @Test
    public void controllerTest() throws Exception {
        mok.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/view.jsp"));
    }
}
