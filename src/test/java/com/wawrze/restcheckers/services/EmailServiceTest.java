package com.wawrze.restcheckers.services;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    private static int counter = 1;

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("EmailService tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("EmailService tests: finished");
    }

    @Before
    public void before(){
        System.out.println("Test #" + counter + ": started");
    }

    @After
    public void after(){
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Ignore
    @Test
    public void shouldSentEmail() {
        //Given
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo("test@test.com");
        emailMessage.setSubject("test");
        emailMessage.setText("test");
        //When
        emailService.send("test@test.com", "test", "test");
        //Then
        verify(javaMailSender, times(1)).send(emailMessage);
    }

}