package com.project.stock.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

/**
 * Classe utilitaire pour la sérialisation/désérialisation JSON
 */
public class JsonHelper {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Convertit un objet en JSON
     * @param object Objet à convertir
     * @return String JSON
     * @throws IOException En cas d'erreur de sérialisation
     */
    public static String toJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Convertit une liste d'objets en JSON
     * @param list Liste à convertir
     * @return String JSON
     * @throws IOException En cas d'erreur de sérialisation
     */
    public static String listToJson(List<?> list) throws IOException {
        return objectMapper.writeValueAsString(list);
    }

    /**
     * Convertit un JSON en objet
     * @param json String JSON
     * @param clazz Classe de l'objet cible
     * @param <T> Type de l'objet
     * @return Objet désérialisé
     * @throws IOException En cas d'erreur de désérialisation
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * Convertit un JSON en liste d'objets
     * @param json String JSON
     * @param typeReference TypeReference pour la liste
     * @param <T> Type des objets dans la liste
     * @return Liste d'objets désérialisés
     * @throws IOException En cas d'erreur de désérialisation
     */
    public static <T> List<T> listFromJson(String json, TypeReference<List<T>> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }
}




