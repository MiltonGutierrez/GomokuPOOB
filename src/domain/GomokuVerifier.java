/**
 * 
 */
package domain;

/**
 * 
 */
public class GomokuVerifier {
	
	private Gomoku gomoku;
	private boolean gomokuFinished;
	/**
	 * 
	 * @param gomoku
	 */
	public GomokuVerifier(Gomoku gomoku) {
		this.gomoku = gomoku;
		this.gomokuFinished = false;
	}
    /**
     * 
     * @param xPos
     * @param yPos
     * @return
     */
    public boolean validPlay(int xPos, int yPos){
        return gomoku.getTokenMatrix()[xPos][yPos] == null && !gomokuFinished;
    }
    
	/**
	 * 
	 * @return
	 */
	public boolean getGomokuFinished() {
		return gomokuFinished;
	}

    /**
     * 
     * @param xPos
     * @param yPos
     * @return
     */
    public int[] calculationsForWinnerValidations(int xPos, int yPos, int dimension){
        int restaXpos = xPos - 4;
        int sumaXpos = xPos + 4;
        int restaYpos = yPos - 4;
        int sumaYpos = yPos + 4 ;
        while(restaXpos < 0){
            restaXpos++;
        }
        while(sumaXpos >= dimension){
            sumaXpos--;
        }
        while(restaYpos < 0){
            restaYpos++;
        }
        while(sumaYpos >= dimension){
            sumaYpos--;
        }
        return new int[]{restaXpos, sumaXpos, restaYpos, sumaYpos};
    }

    /**
     * 
     * @param restaYpos
     * @param sumaYpos
     * @param xPos
     * @param matrix
     */
    public void winnerValidationOnTheXCoordinate(int restaYpos, int sumaYpos, int xPos,  Token[][] matrix){
        int cont = 0;
        if(!gomokuFinished){
            //Validadion en x
            for(int i = restaYpos ; i <= sumaYpos; i++){
                //System.out.println("i = "+ i+ " cont = " + cont + " result = " + matrix[xPos][i]);
                if(matrix[xPos][i] != null){
                    cont += matrix[xPos][i].getValue();
                }
                if (cont >= 5){
                    gomokuFinished = true;
                    break;
                }
                else if(matrix[xPos][i] == null){
                    cont = 0;
                }
            }
        }

    }

    /**
     * 
     * @param restaXpos
     * @param sumaXpos
     * @param yPos
     * @param matrix
     */
    public void winnerValidationOnTheYCoordinate(int restaXpos, int sumaXpos, int yPos, Token[][] matrix){
        int cont = 0;
        if(!gomokuFinished){
            //Validacion en y
            cont = 0;
            for(int i = restaXpos; i <= sumaXpos; i++){
                //System.out.println("i = "+ i+ " cont = " + cont + " result = " + matrix[i][yPos]);
                if(matrix[i][yPos] != null){
                    cont += matrix[i][yPos].getValue();
                }
                if (cont >= 5){
                    gomokuFinished = true;
                    break;
                }
                else if(matrix[i][yPos] == null){
                    cont = 0;
                }
            }
        }

    }

    /**
     * 
     * @param xPos
     * @param yPos
     * @param dimension
     * @param matrix
     */
    public void winnerValidationOnTheDiagonal1(int xPos, int yPos, int dimension, Token[][] matrix){
            if(!gomokuFinished){
            //Validacion Diagonal 1
            int cont = 0;
            int[] firstNumber1 = getStartingNumberDiagonalWinner1(xPos, yPos);
            int xFirst = firstNumber1[0];
            int yFirst = firstNumber1[1];
            int lastNumber = getLastNumberDiagonalWinner1(xPos, yPos, dimension);
            for(int i = xFirst; i <= xFirst + lastNumber && i < dimension && yFirst < dimension; i++) {
                //System.out.println("i = "+ i + " yFirst = " + yFirst + " cont = " + cont + " result = " + matrix[i][yFirst]);
                if(matrix[i][yFirst] != null){
                    cont += matrix[i][yFirst].getValue();
                }
                if(cont >= 5){
                    gomokuFinished = true;
                    break;
                }
                else if(matrix[i][yFirst] == null){
                    cont = 0;
                }
                yFirst++;
            }
        }
    }
    
    /**
     * 
     * @param xPos
     * @param yPos
     * @param dimension
     * @param matrix
     */
    public void winnerValidationOnTheDiagonal2(int xPos, int yPos, int dimension, Token[][] matrix){
        if(!gomokuFinished){
            //Validacion Diagonal 2
            int cont = 0;
            int[] firstNumber1 = getStartingNumberDiagonalWinner2(xPos, yPos, dimension);
            int xFirst = firstNumber1[0];
            int yFirst = firstNumber1[1];
            int[] lastNumber1 = getLastNumberDiagonalWinner2(xPos, yPos, dimension);
            int yLast = lastNumber1[1];

            for(int i = xFirst; yFirst <= yLast && xFirst >= 0; i--){
                //System.out.println("i = "+ i + " yFirst = " + yFirst + " cont = " + cont + " result = ");
                if(matrix[i][yFirst] != null){
                    cont += matrix[i][yFirst].getValue();
                }
                if(cont >= 5){
                    gomokuFinished = true;
                    break;
                }
                else if(matrix[i][yFirst] == null){
                    cont = 0;
                }
                yFirst++;
            }
        }

    }

    /**
     * 
     * @param xPos
     * @param yPos
     * @param dimension
     * @param matrix
     */
    public void  winner(int xPos, int yPos, int dimension, Token[][] matrix){
        int[] calculations = calculationsForWinnerValidations(xPos, yPos, dimension);
        winnerValidationOnTheXCoordinate(calculations[2], calculations[3], xPos, matrix);
        winnerValidationOnTheYCoordinate(calculations[0], calculations[1],yPos, matrix);
        winnerValidationOnTheDiagonal1(xPos, yPos, dimension, matrix);
        winnerValidationOnTheDiagonal2(xPos, yPos, dimension, matrix);
    }

    /**
     * Returns the values to validate if there's a winner on the diagonal2
     * @param xPos
     * @param yPos
     * @param dimension
     * @return
     */
    public int[] getStartingNumberDiagonalWinner2(int xPos, int yPos, int dimension){
        int cont = 0;
        if(xPos + 1 == dimension || yPos == 0){
            return  new int[]{xPos, yPos};
        }
        while(xPos + 1 < dimension && yPos > 0 && cont < 4){
            xPos++;
            yPos--;
            cont++;
        }
        return new int[]{xPos, yPos};

    }

    /**
     * Returns the values to validate if there's a winner on the diagonal2
     * @param xPos
     * @param yPos
     * @param dimension
     * @return
     */
    public int[] getLastNumberDiagonalWinner2(int xPos, int yPos, int dimension){
        int cont = 0;
        if(yPos + 1 == dimension || xPos == 0){
            return new int[]{xPos,yPos};
        }
        while(xPos > 0 && yPos + 1 < dimension && cont < 4){
            xPos--;
            yPos++;
            cont++;
        }
        return new int[]{xPos, yPos};
    }

    /**
     * Returns the values to validate if there's a winner on the diagonal1
     * @param xPos
     * @param yPos
     * @return
     */
    public int[] getStartingNumberDiagonalWinner1(int xPos, int yPos){
        int cont = 0;
        while (xPos > 0 && yPos> 0 && cont < 4 ) {
            xPos--;
            yPos--;
            cont++;
        }
        return new int[]{xPos, yPos};
    }

    /**
     * Returns the values to validate if there's a winner on the diagonal1
     * @param xPos
     * @param yPos
     * @param dimension
     * @return
     */
    public static int getLastNumberDiagonalWinner1(int xPos, int yPos, int dimension){
        int cont = 0;
        while(xPos < dimension && yPos < dimension && cont < 4){
            xPos++;
            yPos++;
            cont++;
        }
        return yPos;
    }
}
