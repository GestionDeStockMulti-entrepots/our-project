package com.project.stock.services;

import com.project.stock.exceptions.EntrepotNotFound;
import com.project.stock.exceptions.ProduitNotFound;
import com.project.stock.models.MouvementStock;
import com.project.stock.repository.MouvementRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service pour la gestion des mouvements de stock
 */
public class StockService {
    private MouvementRepository mouvementRepository;
    private ProduitService produitService;
    private EntrepotService entrepotService;

    // Stock actuel par entrepôt et produit: Map<codeEntrepot, Map<codeProduit, quantite>>
    private Map<String, Map<String, Integer>> stockActuel;

    public StockService() {
        this.mouvementRepository = new MouvementRepository();
        this.produitService = new ProduitService();
        this.entrepotService = new EntrepotService();
        this.stockActuel = new HashMap<>();
        calculerStockActuel();
    }

    /**
     * Calcule le stock actuel à partir de tous les mouvements
     */
    private void calculerStockActuel() {
        stockActuel.clear();
        List<MouvementStock> mouvements = mouvementRepository.findAll();
        
        for (MouvementStock mouvement : mouvements) {
            String codeProduit = mouvement.getCodeProduit();
            
            switch (mouvement.getType()) {
                case ENTREE:
                    String entrepotDest = mouvement.getCodeEntrepotDestination();
                    ajouterStock(entrepotDest, codeProduit, mouvement.getQuantite());
                    break;
                    
                case SORTIE:
                    String entrepotSource = mouvement.getCodeEntrepotSource();
                    ajouterStock(entrepotSource, codeProduit, -mouvement.getQuantite());
                    break;
                    
                case TRANSFERT:
                    String source = mouvement.getCodeEntrepotSource();
                    String destination = mouvement.getCodeEntrepotDestination();
                    ajouterStock(source, codeProduit, -mouvement.getQuantite());
                    ajouterStock(destination, codeProduit, mouvement.getQuantite());
                    break;
            }
        }
    }

    private void ajouterStock(String codeEntrepot, String codeProduit, int quantite) {
        stockActuel.putIfAbsent(codeEntrepot, new HashMap<>());
        Map<String, Integer> produits = stockActuel.get(codeEntrepot);
        produits.put(codeProduit, produits.getOrDefault(codeProduit, 0) + quantite);
    }

    /**
     * Enregistre une entrée de stock
     */
    public void enregistrerEntree(String codeProduit, String codeEntrepot, int quantite, String commentaire) 
            throws ProduitNotFound, EntrepotNotFound, IOException {
        // Vérifications
        if (!produitService.produitExiste(codeProduit)) {
            throw new ProduitNotFound("Produit avec le code " + codeProduit + " non trouvé.");
        }
        if (!entrepotService.entrepotExiste(codeEntrepot)) {
            throw new EntrepotNotFound("Entrepôt avec le code " + codeEntrepot + " non trouvé.");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive.");
        }

        // Créer le mouvement
        MouvementStock mouvement = new MouvementStock(
            UUID.randomUUID().toString(),
            codeProduit,
            null, // pas d'entrepôt source pour une entrée
            codeEntrepot,
            MouvementStock.TypeMouvement.ENTREE,
            quantite,
            commentaire
        );

        mouvementRepository.add(mouvement);
        ajouterStock(codeEntrepot, codeProduit, quantite);
    }

    /**
     * Enregistre une sortie de stock
     */
    public void enregistrerSortie(String codeProduit, String codeEntrepot, int quantite, String commentaire) 
            throws ProduitNotFound, EntrepotNotFound, IOException {
        // Vérifications
        if (!produitService.produitExiste(codeProduit)) {
            throw new ProduitNotFound("Produit avec le code " + codeProduit + " non trouvé.");
        }
        if (!entrepotService.entrepotExiste(codeEntrepot)) {
            throw new EntrepotNotFound("Entrepôt avec le code " + codeEntrepot + " non trouvé.");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive.");
        }

        // Vérifier le stock disponible
        int stockDisponible = getQuantiteParEntrepot(codeProduit, codeEntrepot);
        if (stockDisponible < quantite) {
            throw new IllegalArgumentException(
                "Stock insuffisant. Stock disponible: " + stockDisponible + ", Quantité demandée: " + quantite
            );
        }

        // Créer le mouvement
        MouvementStock mouvement = new MouvementStock(
            UUID.randomUUID().toString(),
            codeProduit,
            codeEntrepot,
            null, // pas d'entrepôt destination pour une sortie
            MouvementStock.TypeMouvement.SORTIE,
            quantite,
            commentaire
        );

        mouvementRepository.add(mouvement);
        ajouterStock(codeEntrepot, codeProduit, -quantite);
    }

    /**
     * Transfère des produits entre entrepôts
     */
    public void transfererProduit(String codeProduit, String codeEntrepotSource, 
                                  String codeEntrepotDestination, int quantite, String commentaire) 
            throws ProduitNotFound, EntrepotNotFound, IOException {
        // Vérifications
        if (!produitService.produitExiste(codeProduit)) {
            throw new ProduitNotFound("Produit avec le code " + codeProduit + " non trouvé.");
        }
        if (!entrepotService.entrepotExiste(codeEntrepotSource)) {
            throw new EntrepotNotFound("Entrepôt source avec le code " + codeEntrepotSource + " non trouvé.");
        }
        if (!entrepotService.entrepotExiste(codeEntrepotDestination)) {
            throw new EntrepotNotFound("Entrepôt destination avec le code " + codeEntrepotDestination + " non trouvé.");
        }
        if (codeEntrepotSource.equals(codeEntrepotDestination)) {
            throw new IllegalArgumentException("L'entrepôt source et destination doivent être différents.");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive.");
        }

        // Vérifier le stock disponible dans l'entrepôt source
        int stockDisponible = getQuantiteParEntrepot(codeProduit, codeEntrepotSource);
        if (stockDisponible < quantite) {
            throw new IllegalArgumentException(
                "Stock insuffisant dans l'entrepôt source. Stock disponible: " + stockDisponible + 
                ", Quantité demandée: " + quantite
            );
        }

        // Créer le mouvement de transfert
        MouvementStock mouvement = new MouvementStock(
            UUID.randomUUID().toString(),
            codeProduit,
            codeEntrepotSource,
            codeEntrepotDestination,
            MouvementStock.TypeMouvement.TRANSFERT,
            quantite,
            commentaire
        );

        mouvementRepository.add(mouvement);
        ajouterStock(codeEntrepotSource, codeProduit, -quantite);
        ajouterStock(codeEntrepotDestination, codeProduit, quantite);
    }

    /**
     * Retourne la quantité totale d'un produit dans tous les entrepôts
     */
    public int getQuantiteTotale(String codeProduit) {
        return stockActuel.values().stream()
                .mapToInt(produits -> produits.getOrDefault(codeProduit, 0))
                .sum();
    }

    /**
     * Retourne la quantité d'un produit dans un entrepôt spécifique
     */
    public int getQuantiteParEntrepot(String codeProduit, String codeEntrepot) {
        return stockActuel.getOrDefault(codeEntrepot, new HashMap<>())
                .getOrDefault(codeProduit, 0);
    }

    /**
     * Retourne l'historique des mouvements
     */
    public List<MouvementStock> getHistoriqueMouvements() {
        return mouvementRepository.findAll();
    }

    /**
     * Retourne les mouvements filtrés par produit
     */
    public List<MouvementStock> getMouvementsParProduit(String codeProduit) {
        return mouvementRepository.findByProduit(codeProduit);
    }

    /**
     * Retourne les mouvements filtrés par date
     */
    public List<MouvementStock> getMouvementsParDate(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return mouvementRepository.findByDate(dateDebut, dateFin);
    }

    /**
     * Retourne les mouvements filtrés par produit et date
     */
    public List<MouvementStock> getMouvementsParProduitEtDate(String codeProduit, 
                                                              LocalDateTime dateDebut, 
                                                              LocalDateTime dateFin) {
        return mouvementRepository.findByProduitAndDate(codeProduit, dateDebut, dateFin);
    }

    /**
     * Retourne le stock actuel de tous les entrepôts
     */
    public Map<String, Map<String, Integer>> getStockActuel() {
        return new HashMap<>(stockActuel);
    }
}








