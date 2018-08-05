package com.wawrze.restcheckers.figures;

import java.io.Serializable;

public abstract class Figure implements Serializable {

	//true - black, false - white
	protected boolean color;

	public boolean getColor() {
		return this.color;
	}

    public abstract String print(int n);

}