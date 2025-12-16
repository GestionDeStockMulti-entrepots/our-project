package com.project.stock;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.project.stock.exceptions.EntrepotNotFound;
import com.project.stock.exceptions.ProduitNotFound;
import com.project.stock.models.Entrepot;
import com.project.stock.models.MouvementStock;
import com.project.stock.models.Produit;
import com.project.stock.services.EntrepotService;
import com.project.stock.services.ProduitService;
import com.project.stock.services.StockService;

/**
 * Classe principale avec menu interactif pour la gestion de stock multi-entrepôts
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ProduitService produitService = new ProduitService();
    private static EntrepotService entrepotService = new EntrepotService();
    private static StockService stockService = new StockService();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     Système de Gestion de Stock Multi-Entrepôts v1.0       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Bienvenue dans le système de gestion de stock !");
        System.out.println("Gérez vos produits, entrepôts et mouvements de stock facilement.");
        System.out.println();

        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix: ");
            System.out.println();

            try {
                switch (choix) {
                    case 1:
                        menuGestionProduits();
                        break;
                    case 2:
                        menuGestionEntrepots();
                        break;
                    case 3:
                        menuMouvementsStock();
                        break;
                    case 4:
                        menuTransfert();
                        break;
                    case 5:
                        menuConsultation();
                        break;
                    case 6:
                        menuStatistiques();
                        break;
                    case 0:
                        continuer = false;
                        System.out.println("═══════════════════════════════════════════════════════════");
                        System.out.println("Merci d'avoir utilisé le système de gestion de stock !");
                        System.out.println("Au revoir !");
                        System.out.println("═══════════════════════════════════════════════════════════");
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("                        MENU PRINCIPAL                     ");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("1. Gestion des Produits");
        System.out.println("2. Gestion des Entrepôts");
        System.out.println("3. Mouvements de Stock");
        System.out.println("4. Transfert entre Entrepôts");
        System.out.println("5. Consultation");
        System.out.println("6. Statistiques & Rapports");
        System.out.println("0. Quitter");
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    // ==================== MENU GESTION PRODUITS ====================
    private static void menuGestionProduits() {
        boolean retour = false;
        while (!retour) {
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("                  GESTION DES PRODUITS                     ");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Modifier un produit");
            System.out.println("3. Supprimer un produit");
            System.out.println("4. Lister tous les produits");
            System.out.println("5. Rechercher un produit");
            System.out.println("0. Retour au menu principal");
            System.out.println("═══════════════════════════════════════════════════════════");

            int choix = lireEntier("Votre choix: ");
            System.out.println();

            try {
                switch (choix) {
                    case 1:
                        ajouterProduit();
                        break;
                    case 2:
                        modifierProduit();
                        break;
                    case 3:
                        supprimerProduit();
                        break;
                    case 4:
                        listerProduits();
                        break;
                    case 5:
                        rechercherProduit();
                        break;
                    case 0:
                        retour = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void ajouterProduit() throws IOException {
        System.out.println("--- Ajout d'un produit ---");
        String code = lireString("Code du produit: ");
        String nom = lireString("Nom: ");
        String description = lireString("Description: ");
        double prix = lireDouble("Prix unitaire: ");

        produitService.ajouterProduit(code, nom, description, prix);
        System.out.println("✅ Produit ajouté avec succès !");
    }

    private static void modifierProduit() throws ProduitNotFound, IOException {
        System.out.println("--- Modification d'un produit ---");
        String code = lireString("Code du produit à modifier: ");
        String nom = lireString("Nouveau nom: ");
        String description = lireString("Nouvelle description: ");
        double prix = lireDouble("Nouveau prix unitaire: ");

        produitService.modifierProduit(code, nom, description, prix);
        System.out.println("✅ Produit modifié avec succès !");
    }

    private static void supprimerProduit() throws ProduitNotFound, IOException {
        System.out.println("--- Suppression d'un produit ---");
        String code = lireString("Code du produit à supprimer: ");
        
        try {
            Produit produit = produitService.trouverProduit(code);
            System.out.println("\nProduit trouvé:");
            System.out.println("  Code: " + produit.getCode());
            System.out.println("  Nom: " + produit.getNom());
            System.out.println("  Description: " + produit.getDescription());
            
            int qteTotale = stockService.getQuantiteTotale(code);
            if (qteTotale > 0) {
                System.out.println("  ⚠️  ATTENTION: Ce produit a " + qteTotale + " unité(s) en stock !");
            }
            
            String confirmation = lireString("\nÊtes-vous sûr de vouloir supprimer ce produit ? (oui/non): ");
            if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
                produitService.supprimerProduit(code);
                System.out.println("✅ Produit supprimé avec succès !");
            } else {
                System.out.println("❌ Suppression annulée.");
            }
        } catch (ProduitNotFound e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void listerProduits() {
        System.out.println("--- Liste des produits ---");
        List<Produit> produits = produitService.listerProduits();
        if (produits.isEmpty()) {
            System.out.println("Aucun produit enregistré.");
        } else {
            System.out.printf("%-15s %-20s %-30s %-15s%n", "Code", "Nom", "Description", "Prix");
            System.out.println("────────────────────────────────────────────────────────────────────────────");
            for (Produit p : produits) {
                System.out.printf("%-15s %-20s %-30s %-15.2f%n", 
                    p.getCode(), p.getNom(), p.getDescription(), p.getPrixUnitaire());
            }
            System.out.println("────────────────────────────────────────────────────────────────────────────");
            System.out.println("Total: " + produits.size() + " produit(s)");
        }
    }

    private static void rechercherProduit() {
        System.out.println("--- Recherche d'un produit ---");
        String code = lireString("Code du produit: ");
        try {
            Produit produit = produitService.trouverProduit(code);
            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("INFORMATIONS DU PRODUIT");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("Code: " + produit.getCode());
            System.out.println("Nom: " + produit.getNom());
            System.out.println("Description: " + produit.getDescription());
            System.out.println("Prix unitaire: " + produit.getPrixUnitaire() + " DH");
            System.out.println("Quantité totale en stock: " + stockService.getQuantiteTotale(code));
            System.out.println("═══════════════════════════════════════════════════════════");
        } catch (ProduitNotFound e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ==================== MENU GESTION ENTREPOTS ====================
    private static void menuGestionEntrepots() {
        boolean retour = false;
        while (!retour) {
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("                 GESTION DES ENTREPÔTS                    ");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("1. Ajouter un entrepôt");
            System.out.println("2. Modifier un entrepôt");
            System.out.println("3. Supprimer un entrepôt");
            System.out.println("4. Lister tous les entrepôts");
            System.out.println("5. Rechercher un entrepôt");
            System.out.println("0. Retour au menu principal");
            System.out.println("═══════════════════════════════════════════════════════════");

            int choix = lireEntier("Votre choix: ");
            System.out.println();

            try {
                switch (choix) {
                    case 1:
                        ajouterEntrepot();
                        break;
                    case 2:
                        modifierEntrepot();
                        break;
                    case 3:
                        supprimerEntrepot();
                        break;
                    case 4:
                        listerEntrepots();
                        break;
                    case 5:
                        rechercherEntrepot();
                        break;
                    case 0:
                        retour = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void ajouterEntrepot() throws IOException {
        System.out.println("--- Ajout d'un entrepôt ---");
        String code = lireString("Code de l'entrepôt: ");
        String nom = lireString("Nom: ");
        String adresse = lireString("Adresse: ");
        double capacite = lireDouble("Capacité maximale: ");

        entrepotService.ajouterEntrepot(code, nom, adresse, capacite);
        System.out.println("✅ Entrepôt ajouté avec succès !");
    }

    private static void modifierEntrepot() throws EntrepotNotFound, IOException {
        System.out.println("--- Modification d'un entrepôt ---");
        String code = lireString("Code de l'entrepôt à modifier: ");
        String nom = lireString("Nouveau nom: ");
        String adresse = lireString("Nouvelle adresse: ");
        double capacite = lireDouble("Nouvelle capacité maximale: ");

        entrepotService.modifierEntrepot(code, nom, adresse, capacite);
        System.out.println("✅ Entrepôt modifié avec succès !");
    }

    private static void supprimerEntrepot() throws EntrepotNotFound, IOException {
        System.out.println("--- Suppression d'un entrepôt ---");
        String code = lireString("Code de l'entrepôt à supprimer: ");
        
        try {
            Entrepot entrepot = entrepotService.trouverEntrepot(code);
            System.out.println("\nEntrepôt trouvé:");
            System.out.println("  Code: " + entrepot.getCode());
            System.out.println("  Nom: " + entrepot.getNom());
            System.out.println("  Adresse: " + entrepot.getAdresse());
            
            // Vérifier le stock
            List<Produit> produits = produitService.listerProduits();
            int totalStock = 0;
            for (Produit p : produits) {
                totalStock += stockService.getQuantiteParEntrepot(p.getCode(), code);
            }
            if (totalStock > 0) {
                System.out.println("  ⚠️  ATTENTION: Cet entrepôt contient " + totalStock + " unité(s) en stock !");
            }
            
            String confirmation = lireString("\nÊtes-vous sûr de vouloir supprimer cet entrepôt ? (oui/non): ");
            if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
                entrepotService.supprimerEntrepot(code);
                System.out.println("✅ Entrepôt supprimé avec succès !");
            } else {
                System.out.println("❌ Suppression annulée.");
            }
        } catch (EntrepotNotFound e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void listerEntrepots() {
        System.out.println("--- Liste des entrepôts ---");
        List<Entrepot> entrepots = entrepotService.listerEntrepots();
        if (entrepots.isEmpty()) {
            System.out.println("Aucun entrepôt enregistré.");
        } else {
            System.out.printf("%-15s %-20s %-30s %-15s%n", "Code", "Nom", "Adresse", "Capacité Max");
            System.out.println("────────────────────────────────────────────────────────────────────────────");
            for (Entrepot e : entrepots) {
                System.out.printf("%-15s %-20s %-30s %-15.2f%n", 
                    e.getCode(), e.getNom(), e.getAdresse(), e.getCapaciteMax());
            }
            System.out.println("────────────────────────────────────────────────────────────────────────────");
            System.out.println("Total: " + entrepots.size() + " entrepôt(s)");
        }
    }

    private static void rechercherEntrepot() {
        System.out.println("--- Recherche d'un entrepôt ---");
        String code = lireString("Code de l'entrepôt: ");
        try {
            Entrepot entrepot = entrepotService.trouverEntrepot(code);
            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("INFORMATIONS DE L'ENTREPÔT");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("Code: " + entrepot.getCode());
            System.out.println("Nom: " + entrepot.getNom());
            System.out.println("Adresse: " + entrepot.getAdresse());
            System.out.println("Capacité maximale: " + entrepot.getCapaciteMax());
            
            // Afficher le stock de cet entrepôt
            List<Produit> produits = produitService.listerProduits();
            int nbProduits = 0;
            int totalQte = 0;
            for (Produit p : produits) {
                int qte = stockService.getQuantiteParEntrepot(p.getCode(), code);
                if (qte > 0) {
                    nbProduits++;
                    totalQte += qte;
                }
            }
            System.out.println("Nombre de produits différents: " + nbProduits);
            System.out.println("Quantité totale en stock: " + totalQte);
            System.out.println("═══════════════════════════════════════════════════════════");
        } catch (EntrepotNotFound e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ==================== MENU MOUVEMENTS STOCK ====================
    private static void menuMouvementsStock() {
        boolean retour = false;
        while (!retour) {
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("                MOUVEMENTS DE STOCK                       ");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("1. Enregistrer une entrée de stock");
            System.out.println("2. Enregistrer une sortie de stock");
            System.out.println("3. Historique des mouvements");
            System.out.println("0. Retour au menu principal");
            System.out.println("═══════════════════════════════════════════════════════════");

            int choix = lireEntier("Votre choix: ");
            System.out.println();

            try {
                switch (choix) {
                    case 1:
                        enregistrerEntree();
                        break;
                    case 2:
                        enregistrerSortie();
                        break;
                    case 3:
                        afficherHistorique();
                        break;
                    case 0:
                        retour = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void enregistrerEntree() throws ProduitNotFound, EntrepotNotFound, IOException {
        System.out.println("--- Enregistrement d'une entrée de stock ---");
        String codeProduit = lireString("Code du produit: ");
        String codeEntrepot = lireString("Code de l'entrepôt: ");
        int quantite = lireEntier("Quantité: ");
        String commentaire = lireString("Commentaire (optionnel): ");

        stockService.enregistrerEntree(codeProduit, codeEntrepot, quantite, commentaire);
        System.out.println("✅ Entrée de stock enregistrée avec succès !");
    }

    private static void enregistrerSortie() throws ProduitNotFound, EntrepotNotFound, IOException {
        System.out.println("--- Enregistrement d'une sortie de stock ---");
        String codeProduit = lireString("Code du produit: ");
        String codeEntrepot = lireString("Code de l'entrepôt: ");
        int quantite = lireEntier("Quantité: ");
        String commentaire = lireString("Commentaire (optionnel): ");

        stockService.enregistrerSortie(codeProduit, codeEntrepot, quantite, commentaire);
        System.out.println("✅ Sortie de stock enregistrée avec succès !");
    }

    private static void afficherHistorique() {
        System.out.println("--- Historique des mouvements ---");
        List<MouvementStock> mouvements = stockService.getHistoriqueMouvements();
        if (mouvements.isEmpty()) {
            System.out.println("Aucun mouvement enregistré.");
        } else {
            System.out.printf("%-10s %-12s %-15s %-15s %-10s %-8s %-20s %-20s%n", 
                "ID", "Type", "Produit", "Source", "Dest.", "Qté", "Date", "Commentaire");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            for (MouvementStock m : mouvements) {
                System.out.printf("%-10s %-12s %-15s %-15s %-10s %-8d %-20s %-20s%n",
                    m.getId().substring(0, Math.min(8, m.getId().length())),
                    m.getType(),
                    m.getCodeProduit(),
                    m.getCodeEntrepotSource() != null ? m.getCodeEntrepotSource() : "-",
                    m.getCodeEntrepotDestination() != null ? m.getCodeEntrepotDestination() : "-",
                    m.getQuantite(),
                    m.getDateMouvement().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    m.getCommentaire() != null ? m.getCommentaire() : "-"
                );
            }
        }
    }

    // ==================== MENU TRANSFERT ====================
    private static void menuTransfert() {
        System.out.println("--- Transfert entre entrepôts ---");
        try {
            String codeProduit = lireString("Code du produit: ");
            String codeEntrepotSource = lireString("Code de l'entrepôt source: ");
            String codeEntrepotDestination = lireString("Code de l'entrepôt destination: ");
            int quantite = lireEntier("Quantité à transférer: ");
            String commentaire = lireString("Commentaire (optionnel): ");

            stockService.transfererProduit(codeProduit, codeEntrepotSource, codeEntrepotDestination, quantite, commentaire);
            System.out.println("✅ Transfert effectué avec succès !");
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
        }
    }

    // ==================== MENU CONSULTATION ====================
    private static void menuConsultation() {
        boolean retour = false;
        while (!retour) {
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("                    CONSULTATION                          ");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("1. Quantité totale par produit");
            System.out.println("2. Quantité par entrepôt");
            System.out.println("3. Liste des mouvements filtrée par produit");
            System.out.println("4. Liste des mouvements filtrée par date");
            System.out.println("5. Liste des mouvements filtrée par produit et date");
            System.out.println("0. Retour au menu principal");
            System.out.println("═══════════════════════════════════════════════════════════");

            int choix = lireEntier("Votre choix: ");
            System.out.println();

            try {
                switch (choix) {
                    case 1:
                        consulterQuantiteTotale();
                        break;
                    case 2:
                        consulterQuantiteParEntrepot();
                        break;
                    case 3:
                        consulterMouvementsParProduit();
                        break;
                    case 4:
                        consulterMouvementsParDate();
                        break;
                    case 5:
                        consulterMouvementsParProduitEtDate();
                        break;
                    case 0:
                        retour = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void consulterQuantiteTotale() {
        System.out.println("--- Quantité totale par produit ---");
        String codeProduit = lireString("Code du produit: ");
        int quantite = stockService.getQuantiteTotale(codeProduit);
        System.out.println("Quantité totale du produit " + codeProduit + ": " + quantite);
    }

    private static void consulterQuantiteParEntrepot() {
        System.out.println("--- Quantité par entrepôt ---");
        String codeProduit = lireString("Code du produit: ");
        String codeEntrepot = lireString("Code de l'entrepôt: ");
        int quantite = stockService.getQuantiteParEntrepot(codeProduit, codeEntrepot);
        System.out.println("Quantité du produit " + codeProduit + " dans l'entrepôt " + codeEntrepot + ": " + quantite);
    }

    // ==================== MENU STATISTIQUES & RAPPORTS ====================
    private static void menuStatistiques() {
        boolean retour = false;
        while (!retour) {
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("              STATISTIQUES & RAPPORTS                      ");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("1. Vue d'ensemble du stock");
            System.out.println("2. Stock par entrepôt (détaillé)");
            System.out.println("3. Produits en rupture de stock");
            System.out.println("4. Top 10 produits (par quantité totale)");
            System.out.println("5. Statistiques des mouvements");
            System.out.println("6. Rapport complet");
            System.out.println("0. Retour au menu principal");
            System.out.println("═══════════════════════════════════════════════════════════");

            int choix = lireEntier("Votre choix: ");
            System.out.println();

            try {
                switch (choix) {
                    case 1:
                        afficherVueEnsembleStock();
                        break;
                    case 2:
                        afficherStockParEntrepot();
                        break;
                    case 3:
                        afficherProduitsRupture();
                        break;
                    case 4:
                        afficherTopProduits();
                        break;
                    case 5:
                        afficherStatistiquesMouvements();
                        break;
                    case 6:
                        afficherRapportComplet();
                        break;
                    case 0:
                        retour = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void afficherVueEnsembleStock() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                          VUE D'ENSEMBLE DU STOCK                                        ");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        
        List<Produit> produits = produitService.listerProduits();
        List<Entrepot> entrepots = entrepotService.listerEntrepots();
        
        if (produits.isEmpty() || entrepots.isEmpty()) {
            System.out.println("Aucune donnée disponible. Veuillez ajouter des produits et des entrepôts.");
            return;
        }

        // En-tête du tableau
        System.out.printf("%-15s", "Produit");
        for (Entrepot e : entrepots) {
            System.out.printf(" %-12s", e.getCode());
        }
        System.out.printf(" %-12s%n", "TOTAL");
        System.out.println("────────────────────────────────────────────────────────────────────────────────────────────");

        // Corps du tableau
        for (Produit p : produits) {
            int totalProduit = 0;
            System.out.printf("%-15s", p.getCode());
            for (Entrepot e : entrepots) {
                int qte = stockService.getQuantiteParEntrepot(p.getCode(), e.getCode());
                totalProduit += qte;
                System.out.printf(" %-12d", qte);
            }
            System.out.printf(" %-12d%n", totalProduit);
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
    }

    private static void afficherStockParEntrepot() {
        System.out.println("--- Stock détaillé par entrepôt ---");
        List<Entrepot> entrepots = entrepotService.listerEntrepots();
        
        if (entrepots.isEmpty()) {
            System.out.println("Aucun entrepôt enregistré.");
            return;
        }

        for (Entrepot e : entrepots) {
            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("ENTREPÔT: " + e.getNom() + " (" + e.getCode() + ")");
            System.out.println("Adresse: " + e.getAdresse());
            System.out.println("Capacité max: " + e.getCapaciteMax());
            System.out.println("═══════════════════════════════════════════════════════════");
            
            List<Produit> produits = produitService.listerProduits();
            boolean hasStock = false;
            
            System.out.printf("%-15s %-25s %-12s%n", "Code Produit", "Nom", "Quantité");
            System.out.println("────────────────────────────────────────────────────────────");
            
            for (Produit p : produits) {
                int qte = stockService.getQuantiteParEntrepot(p.getCode(), e.getCode());
                if (qte > 0) {
                    hasStock = true;
                    System.out.printf("%-15s %-25s %-12d%n", p.getCode(), p.getNom(), qte);
                }
            }
            
            if (!hasStock) {
                System.out.println("Aucun stock dans cet entrepôt.");
            }
        }
    }

    private static void afficherProduitsRupture() {
        System.out.println("--- Produits en rupture de stock ---");
        List<Produit> produits = produitService.listerProduits();
        List<Produit> produitsRupture = new java.util.ArrayList<>();
        
        for (Produit p : produits) {
            int total = stockService.getQuantiteTotale(p.getCode());
            if (total == 0) {
                produitsRupture.add(p);
            }
        }
        
        if (produitsRupture.isEmpty()) {
            System.out.println("✅ Aucun produit en rupture de stock.");
        } else {
            System.out.println("⚠️  " + produitsRupture.size() + " produit(s) en rupture de stock:");
            System.out.printf("%-15s %-25s %-30s%n", "Code", "Nom", "Description");
            System.out.println("────────────────────────────────────────────────────────────────────────────");
            for (Produit p : produitsRupture) {
                System.out.printf("%-15s %-25s %-30s%n", p.getCode(), p.getNom(), p.getDescription());
            }
        }
    }

    private static void afficherTopProduits() {
        System.out.println("--- Top 10 produits (par quantité totale) ---");
        List<Produit> produits = produitService.listerProduits();
        
        if (produits.isEmpty()) {
            System.out.println("Aucun produit enregistré.");
            return;
        }

        // Créer une liste avec quantités
        java.util.List<java.util.Map.Entry<Produit, Integer>> produitsAvecQte = new java.util.ArrayList<>();
        for (Produit p : produits) {
            int qte = stockService.getQuantiteTotale(p.getCode());
            produitsAvecQte.add(new java.util.AbstractMap.SimpleEntry<>(p, qte));
        }
        
        // Trier par quantité décroissante
        produitsAvecQte.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        // Afficher top 10
        System.out.printf("%-5s %-15s %-25s %-12s%n", "Rang", "Code", "Nom", "Quantité");
        System.out.println("────────────────────────────────────────────────────────────");
        int rang = 1;
        for (java.util.Map.Entry<Produit, Integer> entry : produitsAvecQte) {
            if (rang > 10) break;
            System.out.printf("%-5d %-15s %-25s %-12d%n", 
                rang++, entry.getKey().getCode(), entry.getKey().getNom(), entry.getValue());
        }
    }

    private static void afficherStatistiquesMouvements() {
        System.out.println("--- Statistiques des mouvements ---");
        List<MouvementStock> mouvements = stockService.getHistoriqueMouvements();
        
        if (mouvements.isEmpty()) {
            System.out.println("Aucun mouvement enregistré.");
            return;
        }

        int nbEntrees = 0, nbSorties = 0, nbTransferts = 0;
        int totalEntrees = 0, totalSorties = 0;
        
        for (MouvementStock m : mouvements) {
            switch (m.getType()) {
                case ENTREE:
                    nbEntrees++;
                    totalEntrees += m.getQuantite();
                    break;
                case SORTIE:
                    nbSorties++;
                    totalSorties += m.getQuantite();
                    break;
                case TRANSFERT:
                    nbTransferts++;
                    break;
            }
        }
        
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("RÉSUMÉ DES MOUVEMENTS");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Nombre total de mouvements: " + mouvements.size());
        System.out.println("  • Entrées: " + nbEntrees + " (Quantité totale: " + totalEntrees + ")");
        System.out.println("  • Sorties: " + nbSorties + " (Quantité totale: " + totalSorties + ")");
        System.out.println("  • Transferts: " + nbTransferts);
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    private static void afficherRapportComplet() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                              RAPPORT COMPLET                                             ");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Date du rapport: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println();
        
        // Statistiques générales
        List<Produit> produits = produitService.listerProduits();
        List<Entrepot> entrepots = entrepotService.listerEntrepots();
        List<MouvementStock> mouvements = stockService.getHistoriqueMouvements();
        
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("STATISTIQUES GÉNÉRALES");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Nombre de produits: " + produits.size());
        System.out.println("Nombre d'entrepôts: " + entrepots.size());
        System.out.println("Nombre de mouvements: " + mouvements.size());
        System.out.println();
        
        // Vue d'ensemble du stock
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("VUE D'ENSEMBLE DU STOCK");
        System.out.println("═══════════════════════════════════════════════════════════");
        afficherVueEnsembleStock();
        System.out.println();
        
        // Produits en rupture
        System.out.println("═══════════════════════════════════════════════════════════");
        afficherProduitsRupture();
        System.out.println();
        
        // Statistiques des mouvements
        System.out.println("═══════════════════════════════════════════════════════════");
        afficherStatistiquesMouvements();
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
    }

    private static void consulterMouvementsParProduit() {
        System.out.println("--- Mouvements par produit ---");
        String codeProduit = lireString("Code du produit: ");
        List<MouvementStock> mouvements = stockService.getMouvementsParProduit(codeProduit);
        afficherMouvements(mouvements);
    }

    private static void consulterMouvementsParDate() {
        System.out.println("--- Mouvements par date ---");
        System.out.println("Format de date: yyyy-MM-dd HH:mm (ex: 2024-01-15 10:30)");
        LocalDateTime dateDebut = lireDate("Date de début: ");
        LocalDateTime dateFin = lireDate("Date de fin: ");
        List<MouvementStock> mouvements = stockService.getMouvementsParDate(dateDebut, dateFin);
        afficherMouvements(mouvements);
    }

    private static void consulterMouvementsParProduitEtDate() {
        System.out.println("--- Mouvements par produit et date ---");
        String codeProduit = lireString("Code du produit: ");
        System.out.println("Format de date: yyyy-MM-dd HH:mm (ex: 2024-01-15 10:30)");
        LocalDateTime dateDebut = lireDate("Date de début: ");
        LocalDateTime dateFin = lireDate("Date de fin: ");
        List<MouvementStock> mouvements = stockService.getMouvementsParProduitEtDate(codeProduit, dateDebut, dateFin);
        afficherMouvements(mouvements);
    }

    private static void afficherMouvements(List<MouvementStock> mouvements) {
        if (mouvements.isEmpty()) {
            System.out.println("Aucun mouvement trouvé.");
        } else {
            System.out.printf("%-10s %-12s %-15s %-15s %-10s %-8s %-20s %-20s%n", 
                "ID", "Type", "Produit", "Source", "Dest.", "Qté", "Date", "Commentaire");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            for (MouvementStock m : mouvements) {
                System.out.printf("%-10s %-12s %-15s %-15s %-10s %-8d %-20s %-20s%n",
                    m.getId().substring(0, Math.min(8, m.getId().length())),
                    m.getType(),
                    m.getCodeProduit(),
                    m.getCodeEntrepotSource() != null ? m.getCodeEntrepotSource() : "-",
                    m.getCodeEntrepotDestination() != null ? m.getCodeEntrepotDestination() : "-",
                    m.getQuantite(),
                    m.getDateMouvement().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    m.getCommentaire() != null ? (m.getCommentaire().length() > 20 ? m.getCommentaire().substring(0, 17) + "..." : m.getCommentaire()) : "-"
                );
            }
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("Total: " + mouvements.size() + " mouvement(s)");
        }
    }

    // ==================== MÉTHODES UTILITAIRES ====================
    private static String lireString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                int valeur = Integer.parseInt(scanner.nextLine().trim());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
            }
        }
    }

    private static double lireDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double valeur = Double.parseDouble(scanner.nextLine().trim());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre décimal valide.");
            }
        }
    }

    private static LocalDateTime lireDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                String dateStr = scanner.nextLine().trim();
                return LocalDateTime.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide. Utilisez: yyyy-MM-dd HH:mm (ex: 2024-01-15 10:30)");
            }
        }
    }
}


