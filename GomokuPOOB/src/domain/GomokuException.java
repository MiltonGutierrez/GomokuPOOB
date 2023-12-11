package domain;

public class GomokuException extends Exception {
    public static final String INVALID_TIME = "El tiempo deseado es incorrecto";
    public static final String PLAYER_NOT_FOUND = "El jugador no existe";

    public GomokuException(String message) {
        super(message);
    }
    
}
