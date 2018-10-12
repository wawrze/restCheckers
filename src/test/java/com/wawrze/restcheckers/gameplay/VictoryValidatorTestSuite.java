package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Pawn;
import com.wawrze.restcheckers.domain.figures.Queen;
import org.junit.*;

public class VictoryValidatorTestSuite {

    private static int counter = 1;
    
    private VictoryValidator victoryValidator = new VictoryValidator();
    
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

       /*******************
      * REVERSED RULES *
    ******************/

    @Test
    public void testNoEndOfGame1rev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .addFigure('C',4, new Pawn(true))
                .addFigure('D',5, new Pawn(true))
                .addFigure('E',4, new Pawn(true))
                .addFigure('F',7, new Pawn(true))
                .addFigure('H',5, new Queen(true))
                .addFigure('G',8, new Queen(true))
                .addFigure('A',4, new Queen(false))
                .addFigure('B',1, new Pawn(false))
                .addFigure('C',6, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('E',2, new Pawn(false))
                .addFigure('F',5, new Pawn(false))
                .addFigure('H',1, new Pawn(false))
                .addFigure('A',2, new Queen(true))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame2rev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('A',2, new Queen(false))
                .addFigure('C',6, new Pawn(true))
                .addFigure('C',4, new Pawn(false))
                .addFigure('F',1, new Queen(true))
                .addFigure('D',1, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame3rev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('D',5, new Queen(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('F',7, new Pawn(true))
                .addFigure('B',3, new Queen(false))
                .addFigure('C',2, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame4rev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('G',2, new Pawn(true))
                .addFigure('E',2, new Queen(false))
                .addFigure('A',4, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('D',3, new Queen(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame5rev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .addFigure('C',4, new Pawn(true))
                .addFigure('D',5, new Pawn(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('F',7, new Pawn(true))
                .addFigure('H',5, new Queen(true))
                .addFigure('G',8, new Queen(true))
                .addFigure('A',4, new Queen(false))
                .addFigure('B',3, new Pawn(false))
                .addFigure('C',6, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('F',5, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('G',2, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame6rev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Pawn(false))
                .addFigure('D',1, new Pawn(false))
                .addFigure('C',2, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .addFigure('E',4, new Pawn(false))
                .addFigure('G',4, new Pawn(false))
                .addFigure('D',5, new Pawn(false))
                .addFigure('F',5, new Pawn(false))
                .addFigure('H',5, new Pawn(false))
                .addFigure('E',6, new Pawn(true))
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',4, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .addFigure('B',5, new Pawn(true))
                .addFigure('C',4, new Pawn(true))
                .addFigure('F',1, new Pawn(true))
                .addFigure('F',3, new Pawn(true))
                .addFigure('G',2, new Pawn(true))
                .addFigure('H',1, new Pawn(true))
                .addFigure('H',3, new Pawn(true))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoMovesBlackrev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('H',1, new Queen(true))
                .addFigure('H',5, new Queen(false))
                .addFigure('G',2, new Queen(false))
                .addFigure('A',4, new Queen(false))
                .addFigure('B',7, new Pawn(false))
                .addFigure('C',6, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('F',3, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('G',2, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(game.isDraw());
        Assert.assertTrue(game.isWinner());
    }

    @Test
    public void testNoMovesWhiterev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .addFigure('G',2, new Pawn(true))
                .addFigure('G',4, new Pawn(true))
                .addFigure('F',1, new Pawn(true))
                .addFigure('F',3, new Pawn(true))
                .addFigure('H',3, new Queen(false))
                .addFigure('H',5, new Queen(false))
                .addFigure('G',6, new Queen(true))
                .addFigure('G',8, new Queen(true))
                .addFigure('F',5, new Queen(true))
                .addFigure('F',7, new Queen(true))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(false);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(game.isDraw());
        Assert.assertFalse(game.isWinner());
    }

    @Test
    public void testQueens15MovesRev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .addFigure('H',7, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(16);
        game.setBlackQueenMoves(15);
        game.setActivePlayer(true);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(game.isDraw());
    }

    @Test
    public void testNoFiguresBlackRev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Pawn(true))
                .addFigure('B',5, new Pawn(true))
                .addFigure('D',3, new Pawn(true))
                .addFigure('D',7, new Pawn(true))
                .addFigure('A',4, new Queen(true))
                .addFigure('H',7, new Queen(true))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(false);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(game.isWinner());
    }

    @Test
    public void testNoFiguresWhiteRev(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Pawn(false))
                .addFigure('B',5, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('A',4, new Queen(false))
                .addFigure('H',7, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(game.isWinner());
    }

      /*****************
     * STANDARD RULES *
    ****************/

    @Test
    public void testNoEndOfGame1std(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .addFigure('C',4, new Pawn(true))
                .addFigure('D',5, new Pawn(true))
                .addFigure('E',4, new Pawn(true))
                .addFigure('F',7, new Pawn(true))
                .addFigure('H',5, new Queen(true))
                .addFigure('G',8, new Queen(true))
                .addFigure('A',4, new Queen(false))
                .addFigure('B',1, new Pawn(false))
                .addFigure('C',6, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('E',2, new Pawn(false))
                .addFigure('F',5, new Pawn(false))
                .addFigure('H',1, new Pawn(false))
                .addFigure('A',2, new Queen(true))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame2std(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('A',2, new Queen(false))
                .addFigure('C',6, new Pawn(true))
                .addFigure('C',4, new Pawn(false))
                .addFigure('F',1, new Queen(true))
                .addFigure('D',1, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(true);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame3std(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Pawn(true))
                .addFigure('D',5, new Queen(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('F',7, new Pawn(true))
                .addFigure('B',3, new Queen(false))
                .addFigure('C',2, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame4std(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('G',2, new Pawn(true))
                .addFigure('E',2, new Queen(false))
                .addFigure('A',4, new Pawn(true))
                .addFigure('F',3, new Pawn(false))
                .addFigure('D',3, new Queen(false))
                .addFigure('H',7, new Pawn(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame5std(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .addFigure('C',4, new Pawn(true))
                .addFigure('D',5, new Pawn(true))
                .addFigure('E',2, new Pawn(true))
                .addFigure('F',7, new Pawn(true))
                .addFigure('H',5, new Queen(true))
                .addFigure('G',8, new Queen(true))
                .addFigure('A',4, new Queen(false))
                .addFigure('B',3, new Pawn(false))
                .addFigure('C',6, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('F',5, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('G',2, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoEndOfGame6std(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Pawn(false))
                .addFigure('D',1, new Pawn(false))
                .addFigure('C',2, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .addFigure('E',4, new Pawn(false))
                .addFigure('G',4, new Pawn(false))
                .addFigure('D',5, new Pawn(false))
                .addFigure('F',5, new Pawn(false))
                .addFigure('H',5, new Pawn(false))
                .addFigure('E',6, new Pawn(true))
                .addFigure('A',2, new Pawn(true))
                .addFigure('A',4, new Pawn(true))
                .addFigure('B',3, new Pawn(true))
                .addFigure('B',5, new Pawn(true))
                .addFigure('C',4, new Pawn(true))
                .addFigure('F',1, new Pawn(true))
                .addFigure('F',3, new Pawn(true))
                .addFigure('G',2, new Pawn(true))
                .addFigure('H',1, new Pawn(true))
                .addFigure('H',3, new Pawn(true))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result1 = victoryValidator.validateEndOfGame(game);
        game.setActivePlayer(false);
        result2 = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(game.isDraw());
    }

    @Test
    public void testNoMovesBlackstd(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',8, new Pawn(true))
                .addFigure('H',1, new Queen(true))
                .addFigure('H',5, new Queen(false))
                .addFigure('G',2, new Queen(false))
                .addFigure('A',4, new Queen(false))
                .addFigure('B',7, new Pawn(false))
                .addFigure('C',6, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('F',3, new Pawn(false))
                .addFigure('H',3, new Pawn(false))
                .addFigure('G',2, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(game.isDraw());
        Assert.assertFalse(game.isWinner());
    }

    @Test
    public void testNoMovesWhitestd(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('H',1, new Pawn(false))
                .addFigure('H',7, new Pawn(false))
                .addFigure('G',2, new Pawn(true))
                .addFigure('G',4, new Pawn(true))
                .addFigure('F',1, new Pawn(true))
                .addFigure('F',3, new Pawn(true))
                .addFigure('H',3, new Queen(false))
                .addFigure('H',5, new Queen(false))
                .addFigure('G',6, new Queen(true))
                .addFigure('G',8, new Queen(true))
                .addFigure('F',5, new Queen(true))
                .addFigure('F',7, new Queen(true))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(false);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(game.isDraw());
        Assert.assertTrue(game.isWinner());
    }

    @Test
    public void testQueens15Movesstd(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A',2, new Queen(true))
                .addFigure('H',7, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(16);
        game.setBlackQueenMoves(15);
        game.setActivePlayer(true);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(game.isDraw());
    }

    @Test
    public void testNoFiguresBlackstd(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Pawn(true))
                .addFigure('B',5, new Pawn(true))
                .addFigure('D',3, new Pawn(true))
                .addFigure('D',7, new Pawn(true))
                .addFigure('A',4, new Queen(true))
                .addFigure('H',7, new Queen(true))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(false);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(game.isWinner());
    }

    @Test
    public void testNoFiguresWhitestd(){
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('B',1, new Pawn(false))
                .addFigure('B',5, new Pawn(false))
                .addFigure('D',3, new Pawn(false))
                .addFigure('D',7, new Pawn(false))
                .addFigure('A',4, new Queen(false))
                .addFigure('H',7, new Queen(false))
                .build();
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result;
        Game game = new Game("test", ruleSet, false, false);
        //When
        game.setBoard(board);
        game.setWhiteQueenMoves(0);
        game.setBlackQueenMoves(0);
        game.setActivePlayer(true);
        result = victoryValidator.validateEndOfGame(game);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(game.isWinner());
    }

}
