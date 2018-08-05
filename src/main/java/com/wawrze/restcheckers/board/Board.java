package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.gameplay.userInterfaces.InGameUI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Board implements Serializable {

	private HashMap<Character, BoardRow> rows;

	public Board() {
		this.rows = new HashMap<Character, BoardRow>();
        rows.put('A', new BoardRow(true));
        rows.put('B', new BoardRow(false));
        rows.put('C', new BoardRow(true));
        rows.put('D', new BoardRow(false));
        rows.put('E', new BoardRow(true));
        rows.put('F', new BoardRow(false));
        rows.put('G', new BoardRow(true));
        rows.put('H', new BoardRow(false));
	}

	public Board(Board board){
        rows = new HashMap<Character, BoardRow>();
        rows.put('A', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('A').setFigure(i,board.getFigure('A',i));
        rows.put('B', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('B').setFigure(i,board.getFigure('B',i));
        rows.put('C', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('C').setFigure(i,board.getFigure('C',i));
        rows.put('D', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('D').setFigure(i,board.getFigure('D',i));
        rows.put('E', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('E').setFigure(i,board.getFigure('E',i));
        rows.put('F', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('F').setFigure(i,board.getFigure('F',i));
        rows.put('G', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('G').setFigure(i,board.getFigure('G',i));
        rows.put('H', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('H').setFigure(i,board.getFigure('H',i));
    }

    public Figure getFigure(char row, int col) {
		return this.rows.get(row).getFigure(col);
	}

	public void setFigure(char row, int col, Figure figure) {
		this.rows.get(row).setFigure(col, figure);
	}

	public void printBoardSimple(List<String> moves, InGameUI inGameUI){
        System.out.print("     1    2    3    4    5    6    7    8" + inGameUI.sideMenuSimple(1, moves));
        System.out.print("\n  +----+----+----+----+----+----+----+----+" + inGameUI.sideMenuSimple(2, moves));
        System.out.print(rows.get('A').printRowSimple('A', 3, moves, inGameUI));
        System.out.print(rows.get('B').printRowSimple('B', 6, moves, inGameUI));
        System.out.print(rows.get('C').printRowSimple('C', 9, moves, inGameUI));
        System.out.print(rows.get('D').printRowSimple('D', 12, moves, inGameUI));
        System.out.print(rows.get('E').printRowSimple('E', 15, moves, inGameUI));
        System.out.print(rows.get('F').printRowSimple('F', 18, moves, inGameUI));
        System.out.print(rows.get('G').printRowSimple('G', 21, moves, inGameUI));
        System.out.print(rows.get('H').printRowSimple('H', 24, moves, inGameUI));
        System.out.println("\n    1    2    3    4    5    6    7    8\n");
    }

    public void printBoard(List<String> moves, boolean player, InGameUI inGameUI) {
        String board = "";
        board += inGameUI.sideMenu(1, moves, player);
        board += "\n     1      2      3      4      5      6      7      8" + inGameUI.sideMenu(2, moves, player);
        board += "\n ╔════════════════════════════════════════════════════════╗" + inGameUI.sideMenu(3, moves, player);
        board += rows.get('A').printRow('A', 4, moves, player, inGameUI);
        board += rows.get('B').printRow('B', 7, moves, player, inGameUI);
        board += rows.get('C').printRow('C', 10, moves, player, inGameUI);
        board += rows.get('D').printRow('D', 13, moves, player, inGameUI);
        board += rows.get('E').printRow('E', 16, moves, player, inGameUI);
        board += rows.get('F').printRow('F', 19, moves, player, inGameUI);
        board += rows.get('G').printRow('G', 22, moves, player, inGameUI);
        board += rows.get('H').printRow('H', 25, moves, player, inGameUI);
        board += "\n ╚════════════════════════════════════════════════════════╝" + inGameUI.sideMenu(28, moves, player);
        board += "\n     1      2      3      4      5      6      7      8" + inGameUI.sideMenu(29, moves, player);
        System.out.println(board);
    }

}