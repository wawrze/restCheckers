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
    public void testNoCapturesOnBoardWhiteTurn() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Queen queen = new Queen(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,queen);
        board.setFigure('H',7,pawn8);
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
    public void testNoCapturesOnBoardBlackTurn() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen = new Queen(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,queen);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testOneCaptureForBlackPawns() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testTwoCapturesForBlackPawns() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('D',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testThreeCapturesForBlackPawns() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('D',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',5,pawn8);
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
    public void testOneCaptureForWhitePawns() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testTwoCapturesForWhitePawns() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testThreeCapturesForWhitePawns() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('E',4,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testCaptureForBlackPawnInWhiteTurn() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('D',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',5,pawn8);
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
    public void testCaptureForWhitePawnInBlackTurn() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('E',4,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testOneCaptureForWhiteQueen() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Queen queen = new Queen(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',5,queen);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testTwoCapturesForWhiteQueen() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,queen1);
        board.setFigure('F',5,queen2);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testCaptureForWhiteQueenAndWhitePawn() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Queen queen = new Queen(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('E',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',5,queen);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testOneCaptureForBlackQueen() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,queen);
        board.setFigure('G',2,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
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
    public void testTwoCapturesForBlackQueen() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,queen1);
        board.setFigure('C',6,queen2);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',3,pawn8);
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
    public void testCaptureForBlackQueenAndBlackPawn() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,queen);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',3,pawn8);
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
    public void testOneFigureValidator() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen2 = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        int result1, result2,result3;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,queen2);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',3,pawn8);
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
    public void testMaxCaptures() throws IncorrectMoveFormat, IncorrectMoveException{
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Pawn pawn7 = new Pawn(true);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        String result1;
        int result2;
        //When
        board.setFigure('F',5,pawn1);
        board.setFigure('C',4,pawn2);
        board.setFigure('C',6,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('G',2,pawn5);
        board.setFigure('G',4,pawn6);
        board.setFigure('G',6,pawn7);
        board.setFigure('G',6,pawn7);
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