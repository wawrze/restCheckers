package com.wawrze.restcheckers.figures;

import java.io.Serializable;

public class Queen extends Figure implements Serializable {

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