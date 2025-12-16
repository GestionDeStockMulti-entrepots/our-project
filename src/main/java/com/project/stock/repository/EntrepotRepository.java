package com.project.stock.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.stock.models.Entrepot;
import com.project.stock.utils.FileHelper;
import com.project.stock.utils.JsonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository pour la gestion des entrepôts (persistance JSON)
 */
public class EntrepotRepository {
    private static final String FILE_PATH = "data/entrepots.json";
    private List<Entrepot> entrepots;

    public EntrepotRepository() {
        this.entrepots = new ArrayList<>();
        loadEntrepots();
    }

    /**
     * Charge les entrepôts depuis le fichier JSON
     */
    private void loadEntrepots() {
        try {
            String json = FileHelper.readFile(FILE_PATH);
            if (json != null && !json.trim().isEmpty() && !json.equals("[]")) {
                entrepots = JsonHelper.listFromJson(json, new TypeReference<List<Entrepot>>() {});
            }
        } catch (IOException e) {
            entrepots = new ArrayList<>();
        }
    }

    /**
     * Sauvegarde les entrepôts dans le fichier JSON
     */
    private void saveEntrepots() throws IOException {
        String json = JsonHelper.listToJson(entrepots);
        FileHelper.writeFile(FILE_PATH, json);
    }

    /**
     * Ajoute un entrepôt
     */
    public void add(Entrepot entrepot) throws IOException {
        entrepots.add(entrepot);
        saveEntrepots();
    }

    /**
     * Met à jour un entrepôt
     */
    public void update(Entrepot entrepot) throws IOException {
        entrepots = entrepots.stream()
                .map(e -> e.getCode().equals(entrepot.getCode()) ? entrepot : e)
                .collect(Collectors.toList());
        saveEntrepots();
    }

    /**
     * Supprime un entrepôt par son code
     */
    public void delete(String code) throws IOException {
        entrepots = entrepots.stream()
                .filter(e -> !e.getCode().equals(code))
                .collect(Collectors.toList());
        saveEntrepots();
    }

    /**
     * Trouve un entrepôt par son code
     */
    public Entrepot findByCode(String code) {
        return entrepots.stream()
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne tous les entrepôts
     */
    public List<Entrepot> findAll() {
        return new ArrayList<>(entrepots);
    }

    /**
     * Vérifie si un entrepôt existe
     */
    public boolean exists(String code) {
        return entrepots.stream().anyMatch(e -> e.getCode().equals(code));
    }
}




