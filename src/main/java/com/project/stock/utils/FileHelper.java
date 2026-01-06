package com.project.stock.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe utilitaire pour la gestion des fichiers
 */
public class FileHelper {
    
    /**
     * Lit le contenu d'un fichier
     * @param filePath Chemin du fichier
     * @return Contenu du fichier sous forme de String
     * @throws IOException En cas d'erreur de lecture
     */
    public static String readFile(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            return "[]"; // Retourne un tableau JSON vide si le fichier n'existe pas
        }
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Écrit du contenu dans un fichier
     * @param filePath Chemin du fichier
     * @param content Contenu à écrire
     * @throws IOException En cas d'erreur d'écriture
     */
    public static void writeFile(String filePath, String content) throws IOException {
        // Créer le répertoire parent s'il n'existe pas
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        Files.write(Paths.get(filePath), content.getBytes());
    }

    /**
     * Vérifie si un fichier existe
     * @param filePath Chemin du fichier
     * @return true si le fichier existe, false sinon
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}







