package com.wawrze.restcheckers.figures;

public abstract class Figure {

	//true - black, false - white
	protected boolean color;

	public boolean getColor() {
		return this.color;
	}

    public abstract String print(int n);

}