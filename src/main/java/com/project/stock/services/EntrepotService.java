package com.project.stock.services;

import com.project.stock.exceptions.EntrepotNotFound;
import com.project.stock.models.Entrepot;
import com.project.stock.repository.EntrepotRepository;

import java.io.IOException;
import java.util.List;

/**
 * Service pour la gestion des entrepôts
 */
public class EntrepotService {
    private EntrepotRepository entrepotRepository;

    public EntrepotService() {
        this.entrepotRepository = new EntrepotRepository();
    }

    /**
     * Ajoute un entrepôt
     */
    public void ajouterEntrepot(String code, String nom, String adresse, double capaciteMax) 
            throws IOException {
        if (entrepotRepository.exists(code)) {
            throw new IllegalArgumentException("Un entrepôt avec le code " + code + " existe déjà.");
        }
        Entrepot entrepot = new Entrepot(code, nom, adresse, capaciteMax);
        entrepotRepository.add(entrepot);
    }

    /**
     * Modifie un entrepôt
     */
    public void modifierEntrepot(String code, String nom, String adresse, double capaciteMax) 
            throws EntrepotNotFound, IOException {
        Entrepot entrepot = entrepotRepository.findByCode(code);
        if (entrepot == null) {
            throw new EntrepotNotFound("Entrepôt avec le code " + code + " non trouvé.");
        }
        entrepot.setNom(nom);
        entrepot.setAdresse(adresse);
        entrepot.setCapaciteMax(capaciteMax);
        entrepotRepository.update(entrepot);
    }

    /**
     * Supprime un entrepôt
     */
    public void supprimerEntrepot(String code) throws EntrepotNotFound, IOException {
        Entrepot entrepot = entrepotRepository.findByCode(code);
        if (entrepot == null) {
            throw new EntrepotNotFound("Entrepôt avec le code " + code + " non trouvé.");
        }
        entrepotRepository.delete(code);
    }

    /**
     * Liste tous les entrepôts
     */
    public List<Entrepot> listerEntrepots() {
        return entrepotRepository.findAll();
    }

    /**
     * Trouve un entrepôt par son code
     */
    public Entrepot trouverEntrepot(String code) throws EntrepotNotFound {
        Entrepot entrepot = entrepotRepository.findByCode(code);
        if (entrepot == null) {
            throw new EntrepotNotFound("Entrepôt avec le code " + code + " non trouvé.");
        }
        return entrepot;
    }

    /**
     * Vérifie si un entrepôt existe
     */
    public boolean entrepotExiste(String code) {
        return entrepotRepository.exists(code);
    }
}








