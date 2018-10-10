package com.wawrze.restcheckers.domain.figures;

public abstract class Figure {

    public static final String PAWN = "pawn";
    public static final String QUEEN = "queen";
    public static final String NONE = "none";

	protected final boolean color;

	public Figure(final boolean color) {
	    this.color = color;
    }

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