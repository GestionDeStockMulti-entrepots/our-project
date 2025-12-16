package com.project.stock.models;

import java.util.Objects;

/**
 * Classe représentant un entrepôt dans le système de gestion de stock
 */
public class Entrepot {
    private String code;
    private String nom;
    private String adresse;
    private double capaciteMax;

    public Entrepot() {
    }

    public Entrepot(String code, String nom, String adresse, double capaciteMax) {
        this.code = code;
        this.nom = nom;
        this.adresse = adresse;
        this.capaciteMax = capaciteMax;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(double capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrepot entrepot = (Entrepot) o;
        return Objects.equals(code, entrepot.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Entrepot{" +
                "code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", capaciteMax=" + capaciteMax +
                '}';
    }
}




