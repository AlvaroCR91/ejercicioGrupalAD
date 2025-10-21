// 1. CORRECCIÓN: El paquete debe coincidir con tu carpeta
package service;

// 2. CORRECCIÓN: Imports completos a tus paquetes
import model.Periodista;
import repository.PeriodistaDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Capa de servicio para la lógica de negocio de Periodistas.
 * Contiene la Versión 2 (Lógica Java) del ejercicio 15.14.
 */
public class PeriodistaService {

    private PeriodistaDAO periodistaDAO;

    /**
     * Constructor que recibe el DAO (Inyección de Dependencias).
     * El Main creará el DAO y se lo pasará al servicio.
     * (Este constructor ya es correcto y soluciona el error anterior)
     */
    public PeriodistaService(PeriodistaDAO periodistaDAO) {
        this.periodistaDAO = periodistaDAO;
    }

    /**
     * Ej. 15.14 (Versión 2 - Java): Periodistas que NO han escrito artículos.
     * 1. Obtiene TODOS los periodistas del DAO.
     * 2. Filtra la lista en memoria usando Java (streams).
     */
    public List<Periodista> findPeriodistasSinArticulos_Java() {
        // 1. Llama al método "findAll" del DAO
        List<Periodista> todos = periodistaDAO.findPeriodistaEntities();

        // 2. Filtra usando Java
        List<Periodista> resultado = todos.stream()
                .filter(periodista -> periodista.getArticulos().isEmpty())
                .collect(Collectors.toList());

        return resultado;
    }

    /**
     * Ej. 15.14 (Versión 2 - Java): Periodistas con MÁS de 2 artículos.
     * 1. Obtiene TODOS los periodistas del DAO.
     * 2. Filtra la lista en memoria usando Java (streams).
     */
    public List<Periodista> findPeriodistasMasDeDosArticulos_Java() {
        // 1. Llama al método "findAll" del DAO
        List<Periodista> todos = periodistaDAO.findPeriodistaEntities();

        // 2. Filtra usando Java
        List<Periodista> resultado = todos.stream()
                .filter(periodista -> periodista.getArticulos().size() > 2)
                .collect(Collectors.toList());

        return resultado;
    }
}