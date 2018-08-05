package com.wawrze.restcheckers.figures;

import java.io.Serializable;

public class None extends Figure implements Serializable {

    public None(boolean color){
        this.color = color;
    }

	@Override
	public String toString() {
		return "  ";
	}

    @Override
    public String print(int n) {
        if(this.color)
            return "█████";
        else
            return "     ";
    }

}