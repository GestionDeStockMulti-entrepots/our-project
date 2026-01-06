package com.project.stock.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.stock.models.MouvementStock;
import com.project.stock.utils.FileHelper;
import com.project.stock.utils.JsonHelper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository pour la gestion des mouvements de stock (persistance JSON)
 */
public class MouvementRepository {
    private static final String FILE_PATH = "data/mouvements.json";
    private List<MouvementStock> mouvements;

    public MouvementRepository() {
        this.mouvements = new ArrayList<>();
        loadMouvements();
    }

    /**
     * Charge les mouvements depuis le fichier JSON
     */
    private void loadMouvements() {
        try {
            String json = FileHelper.readFile(FILE_PATH);
            if (json != null && !json.trim().isEmpty() && !json.equals("[]")) {
                mouvements = JsonHelper.listFromJson(json, new TypeReference<List<MouvementStock>>() {});
            }
        } catch (IOException e) {
            mouvements = new ArrayList<>();
        }
    }

    /**
     * Sauvegarde les mouvements dans le fichier JSON
     */
    private void saveMouvements() throws IOException {
        String json = JsonHelper.listToJson(mouvements);
        FileHelper.writeFile(FILE_PATH, json);
    }

    /**
     * Ajoute un mouvement
     */
    public void add(MouvementStock mouvement) throws IOException {
        mouvements.add(mouvement);
        saveMouvements();
    }

    /**
     * Trouve un mouvement par son ID
     */
    public MouvementStock findById(String id) {
        return mouvements.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne tous les mouvements
     */
    public List<MouvementStock> findAll() {
        return new ArrayList<>(mouvements);
    }

    /**
     * Retourne les mouvements filtrés par produit
     */
    public List<MouvementStock> findByProduit(String codeProduit) {
        return mouvements.stream()
                .filter(m -> m.getCodeProduit().equals(codeProduit))
                .collect(Collectors.toList());
    }

    /**
     * Retourne les mouvements filtrés par entrepôt
     */
    public List<MouvementStock> findByEntrepot(String codeEntrepot) {
        return mouvements.stream()
                .filter(m -> codeEntrepot.equals(m.getCodeEntrepotSource()) || 
                            codeEntrepot.equals(m.getCodeEntrepotDestination()))
                .collect(Collectors.toList());
    }

    /**
     * Retourne les mouvements filtrés par date
     */
    public List<MouvementStock> findByDate(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return mouvements.stream()
                .filter(m -> !m.getDateMouvement().isBefore(dateDebut) && 
                            !m.getDateMouvement().isAfter(dateFin))
                .collect(Collectors.toList());
    }

    /**
     * Retourne les mouvements filtrés par produit et date
     */
    public List<MouvementStock> findByProduitAndDate(String codeProduit, 
                                                      LocalDateTime dateDebut, 
                                                      LocalDateTime dateFin) {
        return mouvements.stream()
                .filter(m -> m.getCodeProduit().equals(codeProduit) &&
                            !m.getDateMouvement().isBefore(dateDebut) && 
                            !m.getDateMouvement().isAfter(dateFin))
                .collect(Collectors.toList());
    }
}







