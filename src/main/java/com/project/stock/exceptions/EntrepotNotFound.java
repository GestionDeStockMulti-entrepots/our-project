package com.project.stock.exceptions;

/**
 * Exception levée lorsqu'un entrepôt n'est pas trouvé
 */
public class EntrepotNotFound extends Exception {
    public EntrepotNotFound(String message) {
        super(message);
    }

    public EntrepotNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}





