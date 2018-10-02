package com.wawrze.restcheckers.figures;

import org.springframework.stereotype.Service;

@Service
public class FigureFactory {

    public Figure getNewFigure(boolean color, String figureName) {
        switch(figureName) {
            case Figure.PAWN:
                return new Pawn(color);
            case Figure.QUEEN:
                return new Queen(color);
            default:
                return new None(color);
        }
    }

}