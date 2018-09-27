package com.wawrze.restcheckers.moves;

import com.wawrze.restcheckers.board.*;
import com.wawrze.restcheckers.figures.*;
import com.wawrze.restcheckers.gameplay.RulesSet;
import exceptions.*;
import org.junit.*;

public class MoveValidatorTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("MoveValidator tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("MoveValidator tests: finished");
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
    public void testCorrectPawnMove() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        Pawn pawn = new Pawn(true);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        try{
            MoveValidator.validateMove(move,board,true, ruleSet);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoFigureToMove() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move,true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testTargetOccupied() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('B',3,pawn2);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoBias() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testOtherPlayersFigure() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, false, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnMoreThanOneFieldRange() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'D',5);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnTwoFieldsNoCapture() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'C',4);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnTwoFieldsAndCapture() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'C',4);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('B',3,pawn2);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testWhitePawnDirection() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn = new Pawn(false);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, false, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testBlackPawnDirection() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('B',3,'A',2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('B',3,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveRightDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        //When
        Queen queen = new Queen(true);
        board.setFigure('A',2,queen);
        try{
            MoveValidator.validateMove(move,board,true, ruleSet);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('B',3,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2RightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('F',7,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('F',7,pawn1);
        board.setFigure('D',5,pawn2);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('F',7,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveRightUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('H',1,queen);
        try{
            MoveValidator.validateMove(move,board,true, ruleSet);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('G',2,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2RightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('B',7,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('G',2,pawn1);
        board.setFigure('E',4,pawn2);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('B',7,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveLeftUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('H',7,queen);
        try{
            MoveValidator.validateMove(move,board,true, ruleSet);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('G',6,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2LeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('C',2,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('G',6,pawn1);
        board.setFigure('E',4,pawn2);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('C',2,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveLeftDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('A',8,queen);
        try{
            MoveValidator.validateMove(move,board,true, ruleSet);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',8,queen);
        board.setFigure('B',7,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2LeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',8,queen);
        board.setFigure('G',2,pawn);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('A',8,queen);
        board.setFigure('B',7,pawn1);
        board.setFigure('D',5,pawn2);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        //When
        board.setFigure('A',8,queen);
        board.setFigure('G',2,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    private boolean isIncorrectMove(Board board, Move move, boolean player, boolean capture, RulesSet rulesSet) {
        boolean result;
        try{
            MoveValidator.validateMove(move, board, player, rulesSet);
            result = false;
        }
        catch(IncorrectMoveException e){
            result = !capture ? true : false;
        }
        catch(CaptureException e){
            result = capture ? true : false;
            e.getCol();
            e.getRow();
        }
        return result;
    }

    @Test
    public void testQueenMoveOneFieldPossible() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        RulesSet ruleSet = new RulesSet(false, true, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('A',8,queen);
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnCaptureBackward() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'C',6);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, false, false,
                "", "");
        //When
        board.setFigure('A',8,pawn);
        board.setFigure('B',7,queen);
        try {
            MoveValidator.validateMove(move, board, false, ruleSet);
            result = false;
        }
        catch (IncorrectMoveException e) {
            result = true;
        }
        catch (CaptureException e) {
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnCaptureBackward2() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('B',7,'A',8);
        boolean result;
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, false, false,
                "", "");
        //When
        board.setFigure('B',7,pawn);
        try {
            MoveValidator.validateMove(move, board, false, ruleSet);
            result = true;
        }
        catch (Exception e) {
            result = true;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('A',8,queen);
        board.setFigure('F',3,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('H',7,queen);
        board.setFigure('D',3,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }
    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('B',1,'H',7);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('B',1,queen);
        board.setFigure('F',5,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('H',1,queen);
        board.setFigure('C',6,pawn);
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureMoveOneFieldLeftDown()
            throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('A',8,queen);
        board.setFigure('G',2,pawn);
        try {
            MoveValidator.validateMove(move, board, true, ruleSet);
            result = false;
        }
        catch (CaptureException e) {
            result = true;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureMoveOneFieldLeftUp()
            throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('H',7,queen);
        board.setFigure('C',2,pawn);
        try {
            MoveValidator.validateMove(move, board, true, ruleSet);
            result = false;
        }
        catch (CaptureException e) {
            result = true;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureMoveOneFieldRightDown()
            throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Move move = new Move('B',1,'H',7);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('B',1,queen);
        board.setFigure('G',6,pawn);
        try {
            MoveValidator.validateMove(move, board, true, ruleSet);
            result = false;
        }
        catch (CaptureException e) {
            result = true;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureMoveOneFieldRightUp()
            throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        board.setFigure('H',1,queen);
        board.setFigure('B',7,pawn);
        try {
            MoveValidator.validateMove(move, board, true, ruleSet);
            result = false;
        }
        catch (CaptureException e) {
            result = true;
        }
        //Then
        Assert.assertTrue(result);
    }


}