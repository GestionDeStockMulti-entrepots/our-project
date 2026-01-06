package com.project.stock.services;

import com.project.stock.exceptions.ProduitNotFound;
import com.project.stock.models.Produit;
import com.project.stock.repository.ProduitRepository;

import java.io.IOException;
import java.util.List;

/**
 * Service pour la gestion des produits
 */
public class ProduitService {
    private ProduitRepository produitRepository;

    public ProduitService() {
        this.produitRepository = new ProduitRepository();
    }

    /**
     * Ajoute un produit
     */
    public void ajouterProduit(String code, String nom, String description, double prixUnitaire) 
            throws IOException {
        if (produitRepository.exists(code)) {
            throw new IllegalArgumentException("Un produit avec le code " + code + " existe déjà.");
        }
        Produit produit = new Produit(code, nom, description, prixUnitaire);
        produitRepository.add(produit);
    }

    /**
     * Modifie un produit
     */
    public void modifierProduit(String code, String nom, String description, double prixUnitaire) 
            throws ProduitNotFound, IOException {
        Produit produit = produitRepository.findByCode(code);
        if (produit == null) {
            throw new ProduitNotFound("Produit avec le code " + code + " non trouvé.");
        }
        produit.setNom(nom);
        produit.setDescription(description);
        produit.setPrixUnitaire(prixUnitaire);
        produitRepository.update(produit);
    }

    /**
     * Supprime un produit
     */
    public void supprimerProduit(String code) throws ProduitNotFound, IOException {
        Produit produit = produitRepository.findByCode(code);
        if (produit == null) {
            throw new ProduitNotFound("Produit avec le code " + code + " non trouvé.");
        }
        produitRepository.delete(code);
    }

    /**
     * Liste tous les produits
     */
    public List<Produit> listerProduits() {
        return produitRepository.findAll();
    }

    /**
     * Trouve un produit par son code
     */
    public Produit trouverProduit(String code) throws ProduitNotFound {
        Produit produit = produitRepository.findByCode(code);
        if (produit == null) {
            throw new ProduitNotFound("Produit avec le code " + code + " non trouvé.");
        }
        return produit;
    }

    /**
     * Vérifie si un produit existe
     */
    public boolean produitExiste(String code) {
        return produitRepository.exists(code);
    }
}








