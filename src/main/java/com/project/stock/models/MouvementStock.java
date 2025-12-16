package com.project.stock.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Classe représentant un mouvement de stock (entrée, sortie ou transfert)
 */
public class MouvementStock {
    public enum TypeMouvement {
        ENTREE,
        SORTIE,
        TRANSFERT
    }

    private String id;
    private String codeProduit;
    private String codeEntrepotSource; // null pour les entrées
    private String codeEntrepotDestination; // null pour les sorties
    private TypeMouvement type;
    private int quantite;
    private LocalDateTime dateMouvement;
    private String commentaire;

    public MouvementStock() {
        this.dateMouvement = LocalDateTime.now();
    }

    public MouvementStock(String id, String codeProduit, String codeEntrepotSource, 
                         String codeEntrepotDestination, TypeMouvement type, 
                         int quantite, String commentaire) {
        this.id = id;
        this.codeProduit = codeProduit;
        this.codeEntrepotSource = codeEntrepotSource;
        this.codeEntrepotDestination = codeEntrepotDestination;
        this.type = type;
        this.quantite = quantite;
        this.commentaire = commentaire;
        this.dateMouvement = LocalDateTime.now();
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeEntrepotSource() {
        return codeEntrepotSource;
    }

    public void setCodeEntrepotSource(String codeEntrepotSource) {
        this.codeEntrepotSource = codeEntrepotSource;
    }

    public String getCodeEntrepotDestination() {
        return codeEntrepotDestination;
    }

    public void setCodeEntrepotDestination(String codeEntrepotDestination) {
        this.codeEntrepotDestination = codeEntrepotDestination;
    }

    public TypeMouvement getType() {
        return type;
    }

    public void setType(TypeMouvement type) {
        this.type = type;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MouvementStock that = (MouvementStock) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "MouvementStock{" +
                "id='" + id + '\'' +
                ", codeProduit='" + codeProduit + '\'' +
                ", codeEntrepotSource='" + codeEntrepotSource + '\'' +
                ", codeEntrepotDestination='" + codeEntrepotDestination + '\'' +
                ", type=" + type +
                ", quantite=" + quantite +
                ", dateMouvement=" + dateMouvement.format(formatter) +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}




