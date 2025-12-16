package com.project.stock.exceptions;

/**
 * Exception levée lorsqu'un produit n'est pas trouvé
 */
public class ProduitNotFound extends Exception {
    public ProduitNotFound(String message) {
        super(message);
    }

    public ProduitNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}


