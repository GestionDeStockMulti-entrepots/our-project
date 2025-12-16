# Système de Gestion de Stock Multi-Entrepôts

Application Java console pour la gestion des produits dans plusieurs entrepôts avec suivi des quantités, des mouvements d'entrée et de sortie, et du transfert entre entrepôts.

## 📋 Table des matières

- [Description](#description)
- [Fonctionnalités](#fonctionnalités)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Structure du projet](#structure-du-projet)
- [Technologies utilisées](#technologies-utilisées)

## 📝 Description

Ce projet consiste en une application Java permettant la gestion des produits dans plusieurs entrepôts, en assurant le suivi des quantités, des mouvements d'entrée et de sortie, et du transfert entre entrepôts.

## ✨ Fonctionnalités

### A. Gestion des produits
- ✅ Ajouter un produit
- ✅ Modifier un produit
- ✅ Supprimer un produit
- ✅ Lister les produits

### B. Gestion des entrepôts
- ✅ Ajouter un entrepôt
- ✅ Modifier un entrepôt
- ✅ Supprimer un entrepôt
- ✅ Lister les entrepôts

### C. Mouvements de stock
- ✅ Enregistrer une entrée de stock
- ✅ Enregistrer une sortie de stock
- ✅ Historique des mouvements

### D. Transfert entre entrepôts
- ✅ Sélectionner un produit
- ✅ Choisir entrepôt source + destination
- ✅ Déduire la quantité de l'entrepôt source
- ✅ Augmenter la quantité dans l'entrepôt destination
- ✅ Enregistrer comme mouvement double (sortie + entrée)

### E. Consultation
- ✅ Quantité totale par produit
- ✅ Quantité par entrepôt
- ✅ Liste des mouvements filtrée par date / produit
- ✅ Liste des mouvements filtrée par produit et date

## 🏗️ Architecture

L'application est structurée en plusieurs couches :

```
src/main/java/com/project/stock/
├── models/          → Classes métiers (Produit, Entrepot, MouvementStock)
├── services/        → Logique métier (ProduitService, EntrepotService, StockService)
├── repository/      → Gestion du stockage (fichiers JSON)
├── utils/           → Outils de lecture/écriture fichiers et JSON
├── exceptions/      → Exceptions personnalisées
└── Main.java        → Menu principal interactif
```

## 🔧 Prérequis

- Java JDK 11 ou supérieur
- Maven 3.6 ou supérieur (optionnel, pour la gestion des dépendances)

## 📦 Installation

1. **Cloner ou télécharger le projet**

2. **Compiler le projet avec Maven** (si vous utilisez Maven) :
```bash
mvn clean compile
```

3. **Ou compiler manuellement** :
```bash
javac -cp "lib/*" -d target/classes src/main/java/com/project/stock/**/*.java
```

## 🚀 Utilisation

### Avec Maven :
```bash
mvn exec:java
```

### Sans Maven :
```bash
java -cp "target/classes:lib/*" com.project.stock.Main
```

### Menu principal

L'application propose un menu interactif avec les options suivantes :

1. **Gestion des Produits** : CRUD complet sur les produits
2. **Gestion des Entrepôts** : CRUD complet sur les entrepôts
3. **Mouvements de Stock** : Enregistrement des entrées/sorties et consultation de l'historique
4. **Transfert entre Entrepôts** : Transfert de produits d'un entrepôt à un autre
5. **Consultation** : Consultation des stocks et mouvements avec filtres

## 📁 Structure du projet

```
java-multi-entrepots-stock/
│
├── README.md
├── pom.xml
│
├── src/
│   └── main/java/com/project/stock/
│       ├── models/
│       │   ├── Produit.java
│       │   ├── Entrepot.java
│       │   └── MouvementStock.java
│       │
│       ├── services/
│       │   ├── ProduitService.java
│       │   ├── EntrepotService.java
│       │   └── StockService.java
│       │
│       ├── repository/
│       │   ├── ProduitRepository.java
│       │   ├── EntrepotRepository.java
│       │   └── MouvementRepository.java
│       │
│       ├── utils/
│       │   ├── FileHelper.java
│       │   └── JsonHelper.java
│       │
│       ├── exceptions/
│       │   ├── EntrepotNotFound.java
│       │   └── ProduitNotFound.java
│       │
│       └── Main.java
│
├── data/                    (créé automatiquement)
│   ├── produits.json
│   ├── entrepots.json
│   └── mouvements.json
│
└── docs/
    ├── uml.png
    ├── diagramme_classes.png
    └── diagramme_sequence.png
```

## 💾 Persistance des données

Les données sont sauvegardées dans des fichiers JSON dans le répertoire `data/` :
- `data/produits.json` : Liste des produits
- `data/entrepots.json` : Liste des entrepôts
- `data/mouvements.json` : Historique des mouvements de stock

## 🛠️ Technologies utilisées

- **Java 11** : Langage de programmation
- **Jackson** : Bibliothèque pour la sérialisation/désérialisation JSON
- **Maven** : Gestion des dépendances et build (optionnel)

## 📊 Fonctionnalités avancées

- **Gestion du stock en temps réel** : Calcul automatique des quantités disponibles
- **Validation des opérations** : Vérification du stock disponible avant les sorties
- **Historique complet** : Traçabilité de tous les mouvements
- **Filtres de recherche** : Recherche par produit, date, ou combinaison des deux
- **Gestion des erreurs** : Exceptions personnalisées pour une meilleure gestion des erreurs

## 📝 Notes

- Les fichiers JSON sont créés automatiquement lors de la première utilisation
- Le système calcule automatiquement les stocks à partir des mouvements
- Les transferts entre entrepôts sont enregistrés comme des mouvements de type TRANSFERT

## 👤 Auteur

Projet développé dans le cadre d'un système de gestion de stock multi-entrepôts.

## 📄 Licence

Ce projet est fourni à des fins éducatives.




