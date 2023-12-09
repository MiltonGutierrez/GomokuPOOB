package domain;

import java.awt.Color;
import java.util.ArrayList;

public interface Player {
    void setColor(Color color);
    Color getColor();
    void setToken(Token token, int xPos, int yPos, String tokenType);
    void startTime();
    void endTime();
    void calculateTotalTime();
    int getTotalTime();    
    void setTokenMatrix(Token[][] matrix);
    Token[][] returnTokenMatrix();
    //Nuevos metodos.
    String getName();
    void deleteToken(int xPos, int yPos);
	void setTokensToUse(ArrayList<String> tokens);
	String getTokenToUse();
}