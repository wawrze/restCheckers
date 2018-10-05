package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.RulesSets;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.RulesSetDto;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.RulesSetsDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RulesSetsMapperTest {

    @Autowired
    RulesSetsMapper rulesSetsMapper;

    @Autowired
    RulesSets rulesSets;

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("RulesSetsMapper tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("RulesSetsMapper tests: finished");
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

    @Test
    public void testMapToRulesSetDto() {
        //Given
        RulesSet rulesSet = rulesSets.updateRules().get(0);
        RulesSetDto rulesSetDto = new RulesSetDto();
        //When
        rulesSetDto = rulesSetsMapper.mapToRulesSetDto(rulesSet);
        //Then
        assertEquals(rulesSet.getName(), rulesSetDto.getName());
        assertEquals(rulesSet.getDescription(), rulesSetDto.getDescription());
        assertEquals(rulesSet.isCaptureAny(), rulesSetDto.isCaptureAny());
        assertEquals(rulesSet.isPawnCaptureBackward(), rulesSetDto.isPawnCaptureBackward());
        assertEquals(rulesSet.isPawnMoveBackward(), rulesSetDto.isPawnMoveBackward());
        assertEquals(rulesSet.isQueenRangeOne(), rulesSetDto.isQueenRangeOne());
        assertEquals(rulesSet.isQueenRangeOneAfterCapture(), rulesSetDto.isQueenRangeOneAfterCapture());
        assertEquals(rulesSet.isVictoryConditionsReversed(), rulesSetDto.isVictoryConditionsReversed());
    }

    @Test
    public void testMapToRulesSetsDto() {
        //Given
        RulesSetsDto rulesSetsDto = new RulesSetsDto();
        //When
        rulesSetsDto = rulesSetsMapper.mapToRulesSetsDto();
        //Then
        assertEquals(rulesSets.updateRules().size(), rulesSetsDto.getRules().size());
    }

}