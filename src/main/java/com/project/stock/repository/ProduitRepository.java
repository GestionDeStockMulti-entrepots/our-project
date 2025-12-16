package com.project.stock.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.stock.models.Produit;
import com.project.stock.utils.FileHelper;
import com.project.stock.utils.JsonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository pour la gestion des produits (persistance JSON)
 */
public class ProduitRepository {
    private static final String FILE_PATH = "data/produits.json";
    private List<Produit> produits;

    public ProduitRepository() {
        this.produits = new ArrayList<>();
        loadProduits();
    }

    /**
     * Charge les produits depuis le fichier JSON
     */
    private void loadProduits() {
        try {
            String json = FileHelper.readFile(FILE_PATH);
            if (json != null && !json.trim().isEmpty() && !json.equals("[]")) {
                produits = JsonHelper.listFromJson(json, new TypeReference<List<Produit>>() {});
            }
        } catch (IOException e) {
            produits = new ArrayList<>();
        }
    }

    /**
     * Sauvegarde les produits dans le fichier JSON
     */
    private void saveProduits() throws IOException {
        String json = JsonHelper.listToJson(produits);
        FileHelper.writeFile(FILE_PATH, json);
    }

    /**
     * Ajoute un produit
     */
    public void add(Produit produit) throws IOException {
        produits.add(produit);
        saveProduits();
    }

    /**
     * Met à jour un produit
     */
    public void update(Produit produit) throws IOException {
        produits = produits.stream()
                .map(p -> p.getCode().equals(produit.getCode()) ? produit : p)
                .collect(Collectors.toList());
        saveProduits();
    }

    /**
     * Supprime un produit par son code
     */
    public void delete(String code) throws IOException {
        produits = produits.stream()
                .filter(p -> !p.getCode().equals(code))
                .collect(Collectors.toList());
        saveProduits();
    }

    /**
     * Trouve un produit par son code
     */
    public Produit findByCode(String code) {
        return produits.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne tous les produits
     */
    public List<Produit> findAll() {
        return new ArrayList<>(produits);
    }

    /**
     * Vérifie si un produit existe
     */
    public boolean exists(String code) {
        return produits.stream().anyMatch(p -> p.getCode().equals(code));
    }
}




