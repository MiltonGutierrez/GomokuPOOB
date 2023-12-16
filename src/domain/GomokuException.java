package domain;

import java.io.Serializable;

public class GomokuException extends Exception implements Serializable {
    public static final String INVALID_TIME = "El tiempo deseado es incorrecto";
    public static final String PLAYER_NOT_FOUND = "El jugador no existe";
    public static final String TOKEN_INVALID = "La piedra no es valida";
    public static final String BOX_INVALID = "La casilla no es valida";
    public static final String PERCENTAGE_INVALID = "Porcentaje Invalido";
    public static final String INVALID_MACHINE = "Maquina invalida";
    public static final String GENERAL_ERROR = "Ha ocurrido un error general";
    public static final String GOMOKU_HAS_NO_WINNER_YET = "No se han cumplido las condicioens seleccionar un ganardor";

    public GomokuException(String message) {
        super(message);
    }
    
}
