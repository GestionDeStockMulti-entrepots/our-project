package com.project.stock.models;

import java.util.Objects;

/**
 * Classe représentant un produit dans le système de gestion de stock
 */
public class Produit {
    private String code;
    private String nom;
    private String description;
    private double prixUnitaire;

    public Produit() {
    }

    public Produit(String code, String nom, String description, double prixUnitaire) {
        this.code = code;
        this.nom = nom;
        this.description = description;
        this.prixUnitaire = prixUnitaire;
    }

    // Getters et Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return Objects.equals(code, produit.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Produit{" +
                "code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                '}';
    }
}




