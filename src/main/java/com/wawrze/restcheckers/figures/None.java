package com.wawrze.restcheckers.figures;

public class None extends Figure {

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