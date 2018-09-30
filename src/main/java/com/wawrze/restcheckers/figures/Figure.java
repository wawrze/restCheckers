package com.wawrze.restcheckers.figures;

public abstract class Figure {

    public static final String PAWN = "pawn";
    public static final String QUEEN = "queen";
    public static final String NONE = "none";

	//true - black, false - white
	protected boolean color;

	public boolean getColor() {
		return this.color;
	}

    public String getFigureName() {
	    if(this instanceof Pawn)
	        return this.PAWN;
	    else if(this instanceof Queen)
            return this.QUEEN;
	    else
	        return this.NONE;
    }

}