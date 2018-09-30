package com.wawrze.restcheckers.moves;

import com.wawrze.restcheckers.board.*;
import com.wawrze.restcheckers.figures.*;
import com.wawrze.restcheckers.gameplay.RulesSet;
import exceptions.*;
import org.junit.*;

public class CapturePossibilityValidatorTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("CapturePossibilityValidator tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("CapturePossibilityValidator tests: finished");
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
    public void testNoCapturesOnBoardWhiteTurn() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Queen(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = true;
        }
        catch(CapturePossibleException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoCapturesOnBoardBlackTurn() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Queen(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = true;
        }
        catch(CapturePossibleException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testOneCaptureForBlackPawns() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('D',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForBlackPawns() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('D',3, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result);
    }

    @Test
    public void testThreeCapturesForBlackPawns() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('D',3, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('D',5, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testOneCaptureForWhitePawns() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForWhitePawns() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('E',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result);
    }

    @Test
    public void testThreeCapturesForWhitePawns() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('E',4, new Pawn(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('E',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testCaptureForBlackPawnInWhiteTurn() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('D',3, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('D',5, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testOneCaptureForWhiteQueen() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',5, new Queen(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForWhiteQueen() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('F',3, new Queen(false))
                .addFigure('F',5, new Queen(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testCaptureForWhiteQueenAndWhitePawn() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('E',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',5, new Queen(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testOneCaptureForBlackQueen() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Queen(true))
                .addFigure('G',2, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForBlackQueen() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Queen(true))
                .addFigure('C',6, new Queen(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result);
    }

    @Test
    public void testCaptureForBlackQueenAndBlackPawn() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Queen(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        try{
            (new CapturePossibilityValidator(board,true, ruleSet)).validateCapturePossibility();
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testOneFigureValidator() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',6, new Pawn(true))
                .addFigure('C',2, new Pawn(true))
                .addFigure('C',6, new Queen(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('F',7, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result1, result2,result3;
        //When
        try{
            (new CapturePossibilityValidator(board, true, ruleSet)).validateCapturePossibilityForOneFigure('C',6);
            result1 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result1 = sArray.length;
        }
        try{
            (new CapturePossibilityValidator(board, true, ruleSet)).validateCapturePossibilityForOneFigure('C',2);
            result2 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result2 = sArray.length;
        }
        try{
            (new CapturePossibilityValidator(board, true, ruleSet)).validateCapturePossibilityForOneFigure('A',2);
            result3 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result3 = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result1);
        Assert.assertEquals(1,result2);
        Assert.assertEquals(0,result3);
    }

    @Test
    public void testMaxCaptures() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('F',5, new Pawn(false))
                .addFigure('C',4, new Pawn(true))
                .addFigure('C',6, new Pawn(true))
                .addFigure('E',6, new Pawn(true))
                .addFigure('G',2, new Pawn(true))
                .addFigure('G',4, new Pawn(true))
                .addFigure('G',6, new Pawn(true))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        String result1;
        int result2;
        //When
        try{
            (new CapturePossibilityValidator(board,false, ruleSet)).validateCapturePossibility();
            result1 = "";
            result2 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result1 = sArray[0];
            result2 = sArray.length;
        }
        //Then
        Assert.assertEquals("F5-D7",result1);
        Assert.assertEquals(1,result2);
    }

}