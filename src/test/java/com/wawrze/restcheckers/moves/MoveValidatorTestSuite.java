package com.wawrze.restcheckers.moves;

import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Pawn;
import com.wawrze.restcheckers.domain.figures.Queen;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.gameplay.moves.MoveValidator;
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
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .build();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
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
        Board board = new Board.BoardBuilder().build();
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
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .build();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoBias() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .build();
        Move move = new Move('A',2,'B',2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testOtherPlayersFigure() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .build();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, false, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnMoreThanOneFieldRange() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .build();
        Move move = new Move('A',2,'D',5);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnTwoFieldsNoCapture() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .build();
        Move move = new Move('A',2,'C',4);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnTwoFieldsAndCapture() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('B',3, new Pawn(false))
                .build();
        Move move = new Move('A',2,'C',4);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testWhitePawnDirection() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(false))
                .build();
        Move move = new Move('A',2,'B',3);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, false, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testBlackPawnDirection() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',3, new Pawn(true))
                .build();
        Move move = new Move('B',3,'A',2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveRightDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .build();
        Move move = new Move('A',2,'G',8);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .addFigure('B',3, new Pawn(true))
                .build();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2RightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .addFigure('F',7, new Pawn(true))
                .build();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .addFigure('F',7, new Pawn(false))
                .addFigure('D',5, new Pawn(false))
                .build();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .addFigure('F',7, new Pawn(false))
                .build();
        Move move = new Move('A',2,'G',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveRightUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .build();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .addFigure('G',2, new Pawn(true))
                .build();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2RightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .addFigure('B',7, new Pawn(true))
                .build();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .addFigure('G',2, new Pawn(false))
                .addFigure('E',4, new Pawn(false))
                .build();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .addFigure('B',7, new Pawn(false))
                .build();
        Move move = new Move('H',1,'A',8);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveLeftUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .build();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .addFigure('G',6, new Pawn(true))
                .build();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2LeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .addFigure('C',2, new Pawn(true))
                .build();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .addFigure('G',6, new Pawn(false))
                .addFigure('E',4, new Pawn(false))
                .build();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .addFigure('C',2, new Pawn(false))
                .build();
        Move move = new Move('H',7,'B',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveLeftDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .build();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .addFigure('B',7, new Pawn(true))
                .build();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayNoCapture2LeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .addFigure('G',2, new Pawn(true))
                .build();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveTwoFiguresOnWayNoCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .addFigure('B',7, new Pawn(false))
                .addFigure('D',5, new Pawn(false))
                .build();
        Move move = new Move('A',8,'H',1);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('G',2, new Pawn(false))
                .addFigure('A',8, new Queen(true))
                .build();
        Move move = new Move('A',8,'H',1);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveOneFieldPossible() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .build();
        Move move = new Move('A',8,'H',1);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, true, false,
                false, true, true,
                "", "");
        //When
        result = isIncorrectMove(board, move, true, false, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnCaptureBackward() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(false))
                .addFigure('B',7, new Queen(true))
                .build();
        Move move = new Move('A',8,'C',6);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, false, false,
                "", "");
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('B',7, new Pawn(false))
                .build();
        Move move = new Move('B',7,'A',8);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, false, false,
                "", "");
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .addFigure('F',3, new Pawn(false))
                .build();
        Move move = new Move('A',8,'H',1);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .addFigure('D',3, new Pawn(false))
                .build();
        Move move = new Move('H',7,'B',1);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Queen(true))
                .addFigure('F',5, new Pawn(false))
                .build();
        Move move = new Move('B',1,'H',7);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }
    @Test
    public void testQueenMoveFigureOnWayAndNoCaptureMoveOneFieldRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .addFigure('C',6, new Pawn(false))
                .build();
        Move move = new Move('H',1,'A',8);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        result = isIncorrectMove(board, move, true, true, ruleSet);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testQueenMoveFigureOnWayAndCaptureMoveOneFieldLeftDown()
            throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Queen(true))
                .addFigure('G',2, new Pawn(false))
                .build();
        Move move = new Move('A',8,'H',1);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('H',7, new Queen(true))
                .addFigure('C',2, new Pawn(false))
                .build();
        Move move = new Move('H',7,'B',1);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
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
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Queen(true))
                .addFigure('G',6, new Pawn(false))
                .build();
        Move move = new Move('B',1,'H',7);
        boolean result;
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
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
    public void testQueenMoveFigureOnWayAndCaptureMoveOneFieldRightUp() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Queen(true))
                .addFigure('B',7, new Pawn(false))
                .build();
        boolean result;
        Move move = null;
        try {
            move = new Move('H', 1, 'A', 8);
        }
        catch (IncorrectMoveFormat e) {
            result = false;
        }
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, true,
                "", "");
        //When
        try {
            MoveValidator.validateMove(move, board, true, ruleSet);
            result = false;
        }
        catch (CaptureException e) {
            result = true;
        }
        catch (IncorrectMoveException e) {
            result = false;
        }
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

}