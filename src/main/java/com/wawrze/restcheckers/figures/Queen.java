package com.wawrze.restcheckers.figures;

public class Queen extends Figure {

	public Queen(boolean color) {
		this.color = color;
	}

	@Override
	public String toString() {
		if(this.color == true)
			return "QQ";
		else
			return "qq";
	}

    @Override
    public String print(int n) {
        String temp = "";
        switch(n){
            case 0:
                temp = "╔═══╗";
                break;
            case 1:
                if(this.color)
                    temp = "║   ║";
                else
                    temp = "║ █ ║";
                break;
            case 2:
                temp = "╚═══╝";
                break;
        }
        return temp;
    }

}