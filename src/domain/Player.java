package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Timer;

public interface Player extends Serializable{
    void setColor(Color color);
    Color getColor();
    void setToken(Token token, int xPos, int yPos, String tokenType);
    void startTime();
    void stopTime();
    TimePassed getTime(String time);
    void setTokenMatrix(Token[][] matrix);
    Token[][] returnTokenMatrix();
    //Nuevos metodos.
    String getName();
    void deleteToken(int xPos, int yPos);
	void setTokensToUse(ArrayList<String> tokens);
	String getTokenToUse();
	void setTime(Integer time);
	boolean validateTime();
	int getTokensLeft();
	javax.swing.Timer getTimer(String timer);
	void addTokenToUse(String tokenType);
	void setPuntuacion(int score_);
	int getPuntuacion();
}