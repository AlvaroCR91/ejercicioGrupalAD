package es.fempa.acd;

import model.Periodista;
import repository.PeriodistaDAO;
import service.PeriodistaService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // --- INICIALIZACIÓN (La forma correcta) ---

        // 1. Crea el EntityManagerFactory (una sola vez)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PeriodistasPU");

        // 2. Crea el DAO (pasándole el factory)
        PeriodistaDAO periodistaDAO = new PeriodistaDAO(emf);

        // 3. Crea el Servicio (pasándole el DAO)
        PeriodistaService periodistaService = new PeriodistaService(periodistaDAO);


        // --- INFORMES DEL EJERCICIO 15.14 ---

        // ----- Versión 1 (JPQL) - Llama directo al DAO -----
        System.out.println("\n--- Informe JPQL: Periodistas SIN artículos ---");
        List<Periodista> sinArticulos_jpql = periodistaDAO.findPeriodistasSinArticulos_JPQL();
        sinArticulos_jpql.forEach(p -> System.out.println("- " + p.getNombre()));

        System.out.println("\n--- Informe JPQL: Periodistas con MÁS de 2 artículos ---");
        List<Periodista> masDeDos_jpql = periodistaDAO.findPeriodistasMasDeDosArticulos_JPQL();
        masDeDos_jpql.forEach(p -> System.out.println("- " + p.getNombre()));


        // ----- Versión 2 (Lógica Java) - Llama al Servicio -----

        System.out.println("\n--- Informe JAVA: Periodistas SIN artículos ---");
        List<Periodista> sinArticulos_java = periodistaService.findPeriodistasSinArticulos_Java();
        sinArticulos_java.forEach(p -> System.out.println("- " + p.getNombre()));

        System.out.println("\n--- Informe JAVA: Periodistas con MÁS de 2 artículos ---");
        List<Periodista> masDeDos_java = periodistaService.findPeriodistasMasDeDosArticulos_Java();
        masDeDos_java.forEach(p -> System.out.println("- " + p.getNombre()));


        // --- CIERRE ---
        emf.close();
    }
}