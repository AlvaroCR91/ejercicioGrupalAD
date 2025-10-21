package es.fempa.acd.service;

import jakarta.persistence.EntityManagerFactory;
import model.Periodista;
import repository.PeriodistaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PeriodistaService {

    private EntityManagerFactory emf;
    private PeriodistaDAO periodistaDAO;

    public PeriodistaService(PeriodistaDAO periodistaDAO) {
        // Asumo que tu DAO tiene un constructor que obtiene el EntityManagerFactory
        this.periodistaDAO = new PeriodistaDAO(emf);
    }

    /**
     * VERSIÓN DAO/JAVA (15.14): Encuentra periodistas sin artículos.
     * 1. Trae TODOS los periodistas de la BD.
     * 2. Filtra la lista en memoria usando Java.
     */
    public List<Periodista> findPeriodistasSinArticulos_JavaLogic() {
        // 1. Consulta básica del DAO
        List<Periodista> todos = periodistaDAO.findPeriodistaEntities(); // O el nombre de tu método "findAll"

        // 2. Filtra en Java (opción con bucle 'for')
        List<Periodista> resultado = new ArrayList<>();
        for (Periodista p : todos) {
            // Usamos .getArticulos() (del DAO) y .isEmpty() (de Java)
            if (p.getArticulos().isEmpty()) {
                resultado.add(p);
            }
        }
        return resultado;

        /* // Alternativa con Streams (más moderno):
        return todos.stream()
                .filter(p -> p.getArticulos().isEmpty())
                .collect(Collectors.toList());
        */
    }

    /**
     * VERSIÓN DAO/JAVA (15.14): Encuentra periodistas con más de 2 artículos.
     * 1. Trae TODOS los periodistas de la BD.
     * 2. Filtra la lista en memoria usando Java.
     */
    public List<Periodista> findPeriodistasMasDeDosArticulos_JavaLogic() {
        // 1. Consulta básica del DAO
        List<Periodista> todos = periodistaDAO.findPeriodistaEntities(); // O tu método "findAll"

        // 2. Filtra en Java
        return todos.stream()
                .filter(p -> p.getArticulos().size() > 2)
                .collect(Collectors.toList());
    }
}