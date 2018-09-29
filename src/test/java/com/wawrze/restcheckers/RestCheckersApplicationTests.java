package com.wawrze.restcheckers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestCheckersApplicationTests {

    @Test
    public void contextLoads() {
        String[] args = {};
        RestCheckersApplication.main(args);
    }

}
