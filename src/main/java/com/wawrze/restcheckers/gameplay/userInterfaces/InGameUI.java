package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.Menu;
import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.VictoryValidator;
import exceptions.IncorrectMoveFormat;

import java.util.List;
import java.util.Scanner;

public class InGameUI implements UserInterface {

    private String[] options;
    private transient Scanner sc;

    public InGameUI(Scanner sc) {
        String[] tmp = {"h", "p", "s", "x"};
        options = tmp;
        this.sc = sc;
    }

    @Override
    public void printMoveHistory(List<String> moves) {
        if(moves.isEmpty())
            System.out.println("No moves history.");
        else
            for (String m : moves)
                System.out.println(m);
    }

    @Override
    public String getGameName(){
        System.out.println("Name your game:");
        String name;
        do {
            name = sc.nextLine();
        }while(name.isEmpty());
        return name;
    }

    public String sideMenuSimple(int line,List<String> moves){
        String s = "            ";
        switch(line){
            case 1:
                return "\t\t+------+------+-------+";
            case 2:
                return "\t\t| MEN  | KING |       |";
            case 3:
                return "\t\t+------+------+-------+";
            case 4:
                return "\t|  PP  |  QQ  |       |";
            case 5:
                return "\t\t|  PP  |  QQ  | BLACK |";
            case 6:
                return "\t\t+------+------+-------+";
            case 7:
                return "\t|  pp  |  qq  |       |";
            case 8:
                return "\t\t|  pp  |  qq  | WHITE |";
            case 9:
                return "\t\t+------+------+-------+";
            case 10:
                return "\t|   MENU              |";
            case 11:
                return "\t\t|(h) moves history    |";
            case 12:
                return "\t\t|(s) save and exit    |";
            case 13:
                return "\t|(x) exit, no saving  |";
            case 14:
                return "\t\t+---------------------+";
            case 15:
                return "\t\t|   LAST 10 MOVES     |";
            case 16:
                if(moves.size() >= 1)
                    s = moves.get(moves.size() - 1);
                return "\t|    " + s + "     |";
            case 17:
                if(moves.size() >= 2)
                    s = moves.get(moves.size() - 2);
                return "\t\t|    " + s + "     |";
            case 18:
                if(moves.size() >= 3)
                    s = moves.get(moves.size() - 3);
                return "\t\t|    " + s + "     |";
            case 19:
                if(moves.size() >= 4)
                    s = moves.get(moves.size() - 4);
                return "\t|    " + s + "     |";
            case 20:
                if(moves.size() >= 5)
                    s = moves.get(moves.size() - 5);
                return "\t\t|    " + s + "     |";
            case 21:
                if(moves.size() >= 6)
                    s = moves.get(moves.size() - 6);
                return "\t\t|    " + s + "     |";
            case 22:
                if(moves.size() >= 7)
                    s = moves.get(moves.size() - 7);
                return "\t|    " + s + "     |";
            case 23:
                if(moves.size() >= 8)
                    s = moves.get(moves.size() - 8);
                return "\t\t|    " + s + "     |";
            case 24:
                if(moves.size() >= 9)
                    s = moves.get(moves.size() - 9);
                return "\t\t|    " + s + "     |";
            case 25:
                if(moves.size() >= 10)
                    s = moves.get(moves.size() - 10);
                return "\t|    " + s + "     |";
            default:
                return "\t\t+---------------------+";
        }
    }

    @Override
    public String sideMenu(int line,List<String> moves, boolean player){
        String s = "            ";
        switch(line){
            case 1:
                return "\t\t\t\t\t\t\t\t\t╔═══════╦═══════╦════════╗";
            case 2:
                return "\t\t\t║  MEN  ║ KING  ║        ║";
            case 3:
                return "\t\t╠═══════╬═══════╬════════╣";
            case 4:
                if(player)
                    return "\t\t║ ┌───┐ ║ ╔═══╗ ║╔══════╗║";
                else
                    return "\t\t║ ┌───┐ ║ ╔═══╗ ║        ║";
            case 5:
                if(player)
                    return "\t\t║ │   │ ║ ║   ║ ║║BLACK ║║";
                else
                    return "\t\t║ │   │ ║ ║   ║ ║ BLACK  ║";
            case 6:
                if(player)
                    return "\t\t║ └───┘ ║ ╚═══╝ ║╚══════╝║";
                else
                    return "\t\t║ └───┘ ║ ╚═══╝ ║        ║";
            case 7:
                return "\t\t╠═══════╬═══════╬════════╣";
            case 8:
                if(player)
                    return "\t\t║ ┌───┐ ║ ╔═══╗ ║        ║";
                else
                    return "\t\t║ ┌───┐ ║ ╔═══╗ ║╔══════╗║";
            case 9:
                if(player)
                    return "\t\t║ │ █ │ ║ ║ █ ║ ║ WHITE  ║";
                else
                    return "\t\t║ │ █ │ ║ ║ █ ║ ║║WHITE ║║";
            case 10:
                if(player)
                    return "\t\t║ └───┘ ║ ╚═══╝ ║        ║";
                else
                    return "\t\t║ └───┘ ║ ╚═══╝ ║╚══════╝║";
            case 11:
                return "\t\t╠═══════╩═══════╩════════╣";
            case 12:
                return "\t\t║ MENU\t\t\t ║";
            case 13:
                return "\t\t║(h) full moves history  ║";
            case 14:
                return "\t\t║(p) simple board        ║";
            case 15:
                return "\t\t║(s) save and exit       ║";
            case 16:
                return "\t\t║(x) exit without saving ║";
            case 17:
                return "\t\t╠════════════════════════╣";
            case 18:
                return "\t\t║      LAST 10 MOVES\t ║";
            case 19:
                if(moves.size() >= 1)
                    s = moves.get(moves.size() - 1);
                return "\t\t║      " + s + "      ║";
            case 20:
                if(moves.size() >= 2)
                    s = moves.get(moves.size() - 2);
                return "\t\t║      " + s + "      ║";
            case 21:
                if(moves.size() >= 3)
                    s = moves.get(moves.size() - 3);
                return "\t\t║      " + s + "      ║";
            case 22:
                if(moves.size() >= 4)
                    s = moves.get(moves.size() - 4);
                return "\t\t║      " + s + "      ║";
            case 23:
                if(moves.size() >= 5)
                    s = moves.get(moves.size() - 5);
                return "\t\t║      " + s + "      ║";
            case 24:
                if(moves.size() >= 6)
                    s = moves.get(moves.size() - 6);
                return "\t\t║      " + s + "      ║";
            case 25:
                if(moves.size() >= 7)
                    s = moves.get(moves.size() - 7);
                return "\t\t║      " + s + "      ║";
            case 26:
                if(moves.size() >= 8)
                    s = moves.get(moves.size() - 8);
                return "\t\t║      " + s + "      ║";
            case 27:
                if(moves.size() >= 9)
                    s = moves.get(moves.size() - 9);
                return "\t\t║      " + s + "      ║";
            case 28:
                if(moves.size() >= 10)
                    s = moves.get(moves.size() - 10);
                return "\t\t║      " + s + "      ║";
            default:
                return "\t\t\t╚════════════════════════╝";
        }
    }

    public void waitForEnter() {
        System.out.println("Press \"Enter\" to continue.");
        sc.nextLine();
    }

    @Override
    public void printBoard(Board board, boolean simplePrint, boolean player, List<String> moves,
                           RulesSet rulesSet, boolean isItAITurn){
        Menu.cls();
        if(simplePrint) {
            System.out.println(rulesSet);
            board.printBoardSimple(moves, this);
        }
        else {
            Menu.printRulesSet(rulesSet);
            board.printBoard(moves, player, this);
        }
        if(simplePrint)
            System.out.println(" Active player: " + (player ? "BLACK" : "WHITE")
                    + (isItAITurn ? " (computer)\tPlease wait for move." : "\tEnter your next move."));
        else {
            System.out.println(" ╔═════════════════════════════════╦═════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ Active player: " + (player ? "BLACK" : "WHITE") +
                    (isItAITurn ? " (computer) ║ Please wait for move."
                            : "            ║ Enter your next move.")
                     + "\t\t\t\t\t ║ ");
            System.out.println(" ╚═════════════════════════════════╩═════════════════════════════════════════════════════════════╝");
        }
    }

    @Override
    public void printMakingMove(boolean simplePrint, char x1, int y1, char x2, int y2, boolean isItAITurn){
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" Making move: " + x1 + y1 + "-" + x2 + y2);
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ " + complementString("Making move: " + x1 + y1 + "-" + x2 + y2) + " ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    @Override
    public void printMoveDone(boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" Move done.");
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ " + complementString("Move done.") + " ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    @Override
    public void printCaptureDone(boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" Capture done.");
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ " + complementString("Capture done.") + " ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    @Override
    public void printIncorrectMove(String s, boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" Incorrect move: " + s);
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ " + complementString("Incorrect move: " + s) + " ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    @Override
    public void printCapture(String captures, boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" You have to capture: " + captures);
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ " + complementString("You have to capture: " + captures) + " ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    @Override
    public void printMultiCapture(String captures, boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" Possible captures: " + captures);
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ " + complementString("Possible captures: " + captures) + " ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    private String complementString (String s){
        String result = s;
        if(s.length() < 93)
            result += "\t";
        if(s.length() < 85)
            result += "\t";
        if(s.length() < 77)
            result += "\t";
        if(s.length() < 69)
            result += "\t";
        if(s.length() < 61)
            result += "\t";
        if(s.length() < 53)
            result += "\t";
        if(s.length() < 45)
            result += "\t";
        if(s.length() < 37)
            result += "\t";
        if(s.length() < 29)
            result += "\t";
        if(s.length() < 21)
            result += "\t";
        if(s.length() < 13)
            result += "\t";
        return result;
    }

    @Override
    public void printCaptureObligatory(boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if (simplePrint)
            System.out.println(" Capture is obligatory!");
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ Capture is obligatory!\t\t\t\t\t\t\t\t\t ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
        waitForEnter();
    }

    @Override
    public void printIncorrectMoveFormat(boolean simplePrint, boolean isItAITurn) {
        if(isItAITurn)
            return;
        if(simplePrint)
            System.out.println(" Incorrect move format! Proper format example: E4-D5");
        else {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ Incorrect move format! Proper format example: E4-D5\t\t\t\t\t\t ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
        waitForEnter();
    }

    @Override
    public String[] getMoveOrOption(String captures, boolean simplePrint, boolean isItAITurn) {
        String s;
        s = sc.nextLine();
        s = s.toLowerCase();
        String[] result;
        for(String o : options) {
            if (s.equals(o)){
                result = new String[1];
                result[0] = s;
                return result;
            }
        }
        s = s.toUpperCase();
        try{
            validate(s);
            if(captures.isEmpty() || captures.contains(s)) {
                String[] sArray = s.split("-");
                char x1 = sArray[0].charAt(0);
                int y1 = Character.getNumericValue(sArray[0].charAt(1));
                char x2 = sArray[1].charAt(0);
                int y2 = Character.getNumericValue(sArray[1].charAt(1));
                result = new String[4];
                result[0] = "" + x1;
                result[1] = "" + y1;
                result[2] = "" + x2;
                result[3] = "" + y2;
            }
            else{
                printCaptureObligatory(simplePrint, isItAITurn);
                return null;
            }
        }
        catch(IncorrectMoveFormat e){
            printIncorrectMoveFormat(simplePrint, isItAITurn);
            return null;
        }
        return result;
    }

    private void validate(String s) throws IncorrectMoveFormat {
        String[] sArray = s.split("-");
        if (sArray.length != 2)
            throw new IncorrectMoveFormat();
        for (String t : sArray)
            if (t.length() != 2)
                throw new IncorrectMoveFormat();
    }

    @Override
    public boolean endOfGame(Board board, boolean simplePrint, List<String> moves, boolean player) {
        Menu.cls();
        if(simplePrint)
            board.printBoardSimple(moves, this);
        else
            board.printBoard(moves, player, this);
        if(VictoryValidator.isDraw()) {
            if(simplePrint)
                System.out.println("GAME OVER!\n\tDRAW!");
            else {
                System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println(" ║ ███    █   █     █ ████				 					 ║");
                System.out.println(" ║█   █  █ █  ██   ██ █					 					 ║");
                System.out.println(" ║█     █   █ █ █ █ █ █					 	███   ████    █	  █	█	 ║");
                System.out.println(" ║█  ██ █   █ █  █  █ ███				 	█  █  █	  █  █ █  █	█	 ║");
                System.out.println(" ║█   █ █████ █     █ █      ███  █     █ ████ ████   ██	█   █ █   █ █   █ █	█	 ║");
                System.out.println(" ║ ███  █   █ █     █ ████  █   █  █   █  █    █   █  ██	█   █ ████  █   █ █  █	█	 ║");
                System.out.println(" ║			    █   █  █   █  █    █   █  ██	█  █  █	 █  █████ █ █ █	█	 ║");
                System.out.println(" ║			    █   █   █ █   ███  ████   ██        ███   █	  █ █   █ ██   ██	 ║");
                System.out.println(" ║			    █   █   █ █   █    █  █						 ║");
                System.out.println(" ║			     ███     █    ████ █   █  ██					 ║");
                System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
            }
        }
        else {
            if (VictoryValidator.getWinner()) {
                if(simplePrint)
                    System.out.println("GAME OVER!\n\tBLACK WINS!");
                else {
                    System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println(" ║ ███    █   █     █ ████				 	█   █    ██   ██ █		 ║");
                    System.out.println(" ║█   █  █ █  ██   ██ █					 	██  █   █  █ █   █ █		 ║");
                    System.out.println(" ║█     █   █ █ █ █ █ █					 	█ █ █   ████ █   ██		 ║");
                    System.out.println(" ║█  ██ █   █ █  █  █ ███				 	██  ███ █  █  ██ █ █		 ║");
                    System.out.println(" ║█   █ █████ █     █ █      ███  █     █ ████ ████   ██					 ║");
                    System.out.println(" ║ ███  █   █ █     █ ████  █   █  █   █  █    █   █  ██		█   █ █ █   █  ███	 ║");
                    System.out.println(" ║			    █   █  █   █  █    █   █  ██		█   █ █ ██  █ █	 	 ║");
                    System.out.println(" ║			    █   █   █ █   ███  ████   ██  		█ █ █ █ █ █ █  ██	 ║");
                    System.out.println(" ║			    █   █   █ █   █    █  █			██ ██ █ █  ██    █	 ║");
                    System.out.println(" ║			     ███     █    ████ █   █  ██		█   █ █	█   █ ███ 	 ║");
                    System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
                }
            }
            else {
                if(simplePrint)
                    System.out.println("GAME OVER!\n\tWHITE WINS!");
                else {
                    System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println(" ║ ███    █   █     █ ████				 	█   █ █ █ █ ███ ███		 ║");
                    System.out.println(" ║█   █  █ █  ██   ██ █					 	█   █ █ █ █  █  █		 ║");
                    System.out.println(" ║█     █   █ █ █ █ █ █					 	█ █ █ ███ █  █  ██		 ║");
                    System.out.println(" ║█  ██ █   █ █  █  █ ███				 	██ ██ █ █ █  █  ███		 ║");
                    System.out.println(" ║█   █ █████ █     █ █      ███  █     █ ████ ████   ██					 ║");
                    System.out.println(" ║ ███  █   █ █     █ ████  █   █  █   █  █    █   █  ██		█   █ █ █   █  ███	 ║");
                    System.out.println(" ║			    █   █  █   █  █    █   █  ██		█   █ █ ██  █ █	 	 ║");
                    System.out.println(" ║			    █   █   █ █   ███  ████   ██  		█ █ █ █ █ █ █  ██	 ║");
                    System.out.println(" ║			    █   █   █ █   █    █  █			██ ██ █ █  ██    █	 ║");
                    System.out.println(" ║			     ███     █    ████ █   █  ██		█   █ █	█   █ ███	 ║");
                    System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
                }
            }
        }
        String o;
        do{
            o = sc.nextLine();
            if(o.equals("h")){
                System.out.println("Moves history:");
                printMoveHistory(moves);
            }
            else if(o.equals("s"))
                return true;
            else if(o.equals("x"))
                return false;
        }while(true);
    }

}
