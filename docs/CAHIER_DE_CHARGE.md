# CAHIER DES CHARGES
## Système de Gestion de Stock Multi-Entrepôts

---

**Version:** 1.0  
**Date:** 2024  
**Auteur:** Équipe de développement

---

## Table des matières

1. [Introduction](#1-introduction)
2. [Objectifs du projet](#2-objectifs-du-projet)
3. [Fonctionnalités détaillées](#3-fonctionnalités-détaillées)
4. [Architecture technique](#4-architecture-technique)
5. [Contraintes et règles métier](#5-contraintes-et-règles-métier)
6. [Interface utilisateur](#6-interface-utilisateur)
7. [Livrables](#7-livrables)
8. [Tests et validation](#8-tests-et-validation)

---

## 1. Introduction

### 1.1. Contexte

Le présent projet consiste à développer une application Java permettant la gestion des produits dans plusieurs entrepôts, en assurant le suivi des quantités, des mouvements d'entrée et de sortie, et du transfert entre entrepôts.

### 1.2. Objectifs du projet

- Gérer plusieurs entrepôts simultanément
- Gérer un catalogue de produits
- Suivre les mouvements de stock (entrée / sortie)
- Gérer le transfert de produits entre entrepôts
- Consulter l'état des stocks en temps réel
- Générer des statistiques et rapports

### 1.3. Portée du projet

Application console Java avec interface menu interactif, persistance des données en fichiers JSON, et architecture modulaire respectant les principes de la POO.

---

## 2. Objectifs du projet

### 2.1. Objectifs fonctionnels

- **Gestion des produits** : CRUD complet (Créer, Lire, Modifier, Supprimer)
- **Gestion des entrepôts** : CRUD complet
- **Mouvements de stock** : Enregistrement des entrées et sorties
- **Transferts** : Gestion des transferts entre entrepôts
- **Consultation** : Consultation des stocks avec filtres avancés
- **Statistiques** : Génération de rapports et statistiques

### 2.2. Objectifs techniques

- Architecture modulaire et maintenable
- Code Java propre et bien documenté
- Persistance des données en JSON
- Gestion d'erreurs robuste
- Interface utilisateur intuitive

---

## 3. Fonctionnalités détaillées

### 3.1. Gestion des Produits

#### 3.1.1. Ajouter un produit

**Description:** Permet d'ajouter un nouveau produit au catalogue.

**Données requises:**
- Code produit (unique, obligatoire)
- Nom du produit (obligatoire)
- Description (optionnelle)
- Prix unitaire (obligatoire, > 0)

**Contraintes:**
- Le code produit doit être unique
- Le prix doit être positif

**Résultat:** Produit ajouté et sauvegardé dans `data/produits.json`

#### 3.1.2. Modifier un produit

**Description:** Permet de modifier les informations d'un produit existant.

**Données requises:**
- Code produit (existant)
- Nouveau nom
- Nouvelle description
- Nouveau prix unitaire

**Contraintes:**
- Le produit doit exister

**Résultat:** Produit modifié et sauvegardé

#### 3.1.3. Supprimer un produit

**Description:** Permet de supprimer un produit du catalogue.

**Données requises:**
- Code produit (existant)

**Contraintes:**
- Le produit doit exister
- Confirmation requise avant suppression

**Résultat:** Produit supprimé du catalogue

#### 3.1.4. Lister les produits

**Description:** Affiche la liste complète de tous les produits.

**Résultat:** Tableau formaté avec code, nom, description, prix

#### 3.1.5. Rechercher un produit

**Description:** Recherche un produit par son code et affiche ses détails.

**Données requises:**
- Code produit

**Résultat:** Détails complets du produit + quantité totale en stock

---

### 3.2. Gestion des Entrepôts

#### 3.2.1. Ajouter un entrepôt

**Description:** Permet d'ajouter un nouvel entrepôt.

**Données requises:**
- Code entrepôt (unique, obligatoire)
- Nom de l'entrepôt (obligatoire)
- Adresse (obligatoire)
- Capacité maximale (obligatoire, > 0)

**Contraintes:**
- Le code entrepôt doit être unique
- La capacité doit être positive

**Résultat:** Entrepôt ajouté et sauvegardé dans `data/entrepots.json`

#### 3.2.2. Modifier un entrepôt

**Description:** Permet de modifier les informations d'un entrepôt existant.

**Données requises:**
- Code entrepôt (existant)
- Nouveau nom
- Nouvelle adresse
- Nouvelle capacité maximale

**Contraintes:**
- L'entrepôt doit exister

**Résultat:** Entrepôt modifié et sauvegardé

#### 3.2.3. Supprimer un entrepôt

**Description:** Permet de supprimer un entrepôt.

**Données requises:**
- Code entrepôt (existant)

**Contraintes:**
- L'entrepôt doit exister
- Confirmation requise avant suppression
- Avertissement si l'entrepôt contient du stock

**Résultat:** Entrepôt supprimé

#### 3.2.4. Lister les entrepôts

**Description:** Affiche la liste complète de tous les entrepôts.

**Résultat:** Tableau formaté avec code, nom, adresse, capacité max

#### 3.2.5. Rechercher un entrepôt

**Description:** Recherche un entrepôt par son code et affiche ses détails.

**Données requises:**
- Code entrepôt

**Résultat:** Détails complets de l'entrepôt + stock contenu

---

### 3.3. Mouvements de Stock

#### 3.3.1. Enregistrer une entrée de stock

**Description:** Enregistre l'arrivée de produits dans un entrepôt.

**Données requises:**
- Code produit (existant)
- Code entrepôt (existant)
- Quantité (obligatoire, > 0)
- Commentaire (optionnel)

**Contraintes:**
- Le produit doit exister
- L'entrepôt doit exister
- La quantité doit être positive

**Résultat:**
- Mouvement enregistré dans `data/mouvements.json`
- Stock mis à jour automatiquement

#### 3.3.2. Enregistrer une sortie de stock

**Description:** Enregistre la sortie de produits d'un entrepôt.

**Données requises:**
- Code produit (existant)
- Code entrepôt (existant)
- Quantité (obligatoire, > 0)
- Commentaire (optionnel)

**Contraintes:**
- Le produit doit exister
- L'entrepôt doit exister
- La quantité doit être positive
- Stock disponible suffisant

**Résultat:**
- Mouvement enregistré
- Stock déduit automatiquement

#### 3.3.3. Historique des mouvements

**Description:** Affiche l'historique complet de tous les mouvements.

**Résultat:** Tableau avec ID, Type, Produit, Source, Destination, Quantité, Date, Commentaire

---

### 3.4. Transfert entre Entrepôts

#### 3.4.1. Transférer un produit

**Description:** Transfère des produits d'un entrepôt source vers un entrepôt destination.

**Données requises:**
- Code produit (existant)
- Code entrepôt source (existant)
- Code entrepôt destination (existant, différent de la source)
- Quantité (obligatoire, > 0)
- Commentaire (optionnel)

**Contraintes:**
- Le produit doit exister
- Les deux entrepôts doivent exister
- L'entrepôt source et destination doivent être différents
- Stock disponible suffisant dans l'entrepôt source

**Résultat:**
- Mouvement de type TRANSFERT enregistré
- Stock déduit de l'entrepôt source
- Stock ajouté à l'entrepôt destination

---

### 3.5. Consultation

#### 3.5.1. Quantité totale par produit

**Description:** Affiche la quantité totale d'un produit dans tous les entrepôts.

**Données requises:**
- Code produit

**Résultat:** Quantité totale affichée

#### 3.5.2. Quantité par entrepôt

**Description:** Affiche la quantité d'un produit dans un entrepôt spécifique.

**Données requises:**
- Code produit
- Code entrepôt

**Résultat:** Quantité affichée

#### 3.5.3. Liste des mouvements filtrée par produit

**Description:** Affiche tous les mouvements d'un produit spécifique.

**Données requises:**
- Code produit

**Résultat:** Liste filtrée des mouvements

#### 3.5.4. Liste des mouvements filtrée par date

**Description:** Affiche tous les mouvements dans une période donnée.

**Données requises:**
- Date de début (format: yyyy-MM-dd HH:mm)
- Date de fin (format: yyyy-MM-dd HH:mm)

**Résultat:** Liste filtrée des mouvements

#### 3.5.5. Liste des mouvements filtrée par produit et date

**Description:** Affiche les mouvements d'un produit dans une période donnée.

**Données requises:**
- Code produit
- Date de début
- Date de fin

**Résultat:** Liste filtrée des mouvements

---

### 3.6. Statistiques & Rapports

#### 3.6.1. Vue d'ensemble du stock

**Description:** Affiche un tableau récapitulatif du stock par produit et par entrepôt.

**Résultat:** Tableau matriciel avec produits en lignes, entrepôts en colonnes, total par produit

#### 3.6.2. Stock détaillé par entrepôt

**Description:** Affiche le stock détaillé de chaque entrepôt.

**Résultat:** Pour chaque entrepôt, liste des produits avec leurs quantités

#### 3.6.3. Produits en rupture de stock

**Description:** Identifie et affiche les produits avec stock zéro.

**Résultat:** Liste des produits en rupture de stock

#### 3.6.4. Top 10 produits

**Description:** Affiche les 10 produits avec les plus grandes quantités totales.

**Résultat:** Classement décroissant des produits par quantité totale

#### 3.6.5. Statistiques des mouvements

**Description:** Affiche un résumé statistique des mouvements.

**Résultat:**
- Nombre total de mouvements
- Nombre d'entrées (avec quantité totale)
- Nombre de sorties (avec quantité totale)
- Nombre de transferts

#### 3.6.6. Rapport complet

**Description:** Génère un rapport complet avec toutes les statistiques.

**Résultat:** Document récapitulatif incluant:
- Statistiques générales
- Vue d'ensemble du stock
- Produits en rupture
- Statistiques des mouvements

---

## 4. Architecture technique

### 4.1. Structure des packages

```
com.project.stock/
├── models/          → Classes métiers (Produit, Entrepot, MouvementStock)
├── services/        → Logique métier (ProduitService, EntrepotService, StockService)
├── repository/      → Couche de persistance (fichiers JSON)
├── utils/           → Utilitaires (FileHelper, JsonHelper)
├── exceptions/      → Exceptions personnalisées
└── Main.java        → Point d'entrée de l'application
```

### 4.2. Technologies utilisées

- **Langage:** Java 11
- **Bibliothèque JSON:** Jackson 2.15.2
- **Gestion de dépendances:** Maven
- **Format de données:** JSON
- **Interface:** Console avec menu interactif

### 4.3. Persistance des données

Les données sont sauvegardées dans des fichiers JSON:

- `data/produits.json`      → Liste des produits
- `data/entrepots.json`     → Liste des entrepôts
- `data/mouvements.json`    → Historique des mouvements

### 4.4. Principes de conception

- Séparation des responsabilités (Models, Services, Repositories)
- Single Responsibility Principle
- DRY (Don't Repeat Yourself)
- Gestion d'erreurs avec exceptions personnalisées
- Validation des données avant traitement

### 4.5. Diagramme de classes

Le système est organisé en plusieurs couches:

1. **Couche Modèle** : Classes métiers (Produit, Entrepot, MouvementStock)
2. **Couche Service** : Logique métier (ProduitService, EntrepotService, StockService)
3. **Couche Repository** : Accès aux données (ProduitRepository, EntrepotRepository, MouvementRepository)
4. **Couche Utils** : Utilitaires (FileHelper, JsonHelper)
5. **Couche Présentation** : Interface utilisateur (Main)

---

## 5. Contraintes et règles métier

### 5.1. Contraintes de données

- Les codes (produit, entrepôt) doivent être uniques
- Les quantités doivent être positives (> 0)
- Les prix doivent être positifs (> 0)
- Les capacités d'entrepôt doivent être positives (> 0)

### 5.2. Règles métier

- Un produit ne peut pas être supprimé s'il a des mouvements associés (optionnel)
- Une sortie ne peut pas être effectuée si le stock est insuffisant
- Un transfert nécessite un stock suffisant dans l'entrepôt source
- Le stock est calculé automatiquement à partir des mouvements
- Les transferts sont enregistrés comme un seul mouvement de type TRANSFERT

### 5.3. Gestion des erreurs

- **ProduitNotFound:** Levée quand un produit n'existe pas
- **EntrepotNotFound:** Levée quand un entrepôt n'existe pas
- **IllegalArgumentException:** Levée pour données invalides
- **IOException:** Gérée pour les opérations de fichiers

---

## 6. Interface utilisateur

### 6.1. Menu principal

L'application propose un menu principal avec les options suivantes:

1. **Gestion des Produits**
2. **Gestion des Entrepôts**
3. **Mouvements de Stock**
4. **Transfert entre Entrepôts**
5. **Consultation**
6. **Statistiques & Rapports**
0. **Quitter**

### 6.2. Caractéristiques de l'interface

- Menus hiérarchiques clairs
- Messages de confirmation pour les actions importantes
- Affichage formaté en tableaux
- Messages d'erreur explicites
- Validation des saisies utilisateur

### 6.3. Exemples d'interactions

#### Ajouter un produit
```
--- Ajout d'un produit ---
Code du produit: P001
Nom: Ordinateur Portable
Description: Laptop Dell Inspiron 15
Prix unitaire: 4500.00
✅ Produit ajouté avec succès !
```

#### Enregistrer une entrée
```
--- Enregistrement d'une entrée de stock ---
Code du produit: P001
Code de l'entrepôt: E001
Quantité: 10
Commentaire: Réception commande fournisseur
✅ Entrée de stock enregistrée avec succès !
```

---

## 7. Livrables

### 7.1. Code source

- Code Java complet et commenté
- Structure de packages respectée
- Gestion d'erreurs complète
- Documentation du code (JavaDoc)

### 7.2. Documentation

- **README.md** : Instructions d'installation et d'utilisation
- **Cahier des charges** : Ce document
- **Diagrammes UML** : Diagrammes de classes et de séquences
- **Guide utilisateur** : Documentation de l'interface

### 7.3. Fichiers de configuration

- `pom.xml` : Configuration Maven
- Structure de dossiers conforme aux standards Java

### 7.4. Fichiers de données

- `data/produits.json` : Base de données des produits
- `data/entrepots.json` : Base de données des entrepôts
- `data/mouvements.json` : Historique des mouvements

---

## 8. Tests et validation

### 8.1. Scénarios de test recommandés

#### Tests fonctionnels
- Ajout/modification/suppression de produits
- Ajout/modification/suppression d'entrepôts
- Enregistrement d'entrées et sorties
- Transferts entre entrepôts
- Consultation des stocks
- Génération de statistiques

#### Tests de validation
- Vérification de l'unicité des codes
- Vérification des contraintes de données
- Vérification de la cohérence des stocks
- Vérification de la persistance des données

#### Tests d'erreurs
- Produit/entrepôt inexistant
- Stock insuffisant
- Données invalides
- Fichiers corrompus

### 8.2. Validation

- Vérification de l'unicité des codes
- Vérification des contraintes de données
- Vérification de la cohérence des stocks
- Vérification de la persistance des données

### 8.3. Cas d'usage

#### Cas d'usage 1: Gestion complète d'un produit
1. Ajouter un produit
2. Ajouter des produits en stock (entrée)
3. Consulter le stock
4. Effectuer une sortie
5. Consulter l'historique

#### Cas d'usage 2: Transfert entre entrepôts
1. Créer deux entrepôts
2. Ajouter du stock dans l'entrepôt 1
3. Transférer vers l'entrepôt 2
4. Vérifier les stocks des deux entrepôts

#### Cas d'usage 3: Génération de rapports
1. Créer plusieurs produits et entrepôts
2. Effectuer plusieurs mouvements
3. Générer le rapport complet
4. Consulter les statistiques

---

## 9. Améliorations futures (optionnel)

- Interface graphique (JavaFX/Swing)
- Base de données au lieu de fichiers JSON
- Authentification et gestion des utilisateurs
- Export des rapports en PDF/Excel
- Notifications pour produits en rupture
- Historique des modifications (audit trail)
- API REST pour intégration avec d'autres systèmes
- Application mobile
- Multi-utilisateurs avec droits d'accès

---

## 10. Conclusion

Ce cahier des charges définit les spécifications complètes du système de gestion de stock multi-entrepôts. L'application répond aux besoins de gestion des produits, entrepôts et mouvements de stock avec une architecture modulaire et maintenable.

---

**Fin du document**


