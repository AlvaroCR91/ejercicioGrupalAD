package es.fempa.acd;

import java.util.List;
import java.util.Scanner;
import jakarta.persistence.*;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("PeriodistasPU");
        
        PeriodistaDAO perDAO = new PeriodistaDAO(emf);
        ArticuloDAO artDAO = new ArticuloDAO(emf);
        
        int opc;
        do {
            menu();
            opc = new Scanner(System.in).nextInt();
            
            switch(opc) {
                case 1:
                    nuevoPeriodista(perDAO);
                    break;
                case 2:
                    bajaPeriodista(perDAO);
                    break;
                case 3:
                    nuevoArticulo(perDAO, artDAO);
                    break;
                case 4:
                    articulosPeriodista(perDAO);
                    break;
                case 5:
                    articulosAnyo(emf);
                    break;
            }
        } while (opc != 9);
    }
 
    private static void menu() {
        System.out.println("1. Nuevo periodista");
        System.out.println("2. Baja periodista");
        System.out.println("3. Nuevo artículo");
        System.out.println("4. Artículos de un periodista");
        System.out.println("5. Mostrar artículos de un anyo");
        System.out.println("9. Salir");
    }
    
    //Pide los datos de un periodista, comprueba que no exista y, en ese caso
    //lo inserta en la BD.
    static void nuevoPeriodista(PeriodistaDAO perDAO) {
        System.out.println("DNI: ");
        String dni = new Scanner(System.in).nextLine();

        Periodista p = perDAO.findPeriodista(dni);

        if (p != null) {
            System.out.println("El periodista ya está dado de alta.");
            System.out.println(p); //mostamos los datos del periodista.
        } else {

            //seguimos pidiendo sus datos.
            System.out.println("Nombre: ");
            String nombre = new Scanner(System.in).nextLine();

            System.out.println("Número de teléfono: ");
            String numTel = new Scanner(System.in).nextLine();

            p = new Periodista(nombre, dni, numTel); //creamos el periodista

            try {
                perDAO.create(p); //insertamos en la BD
            } catch (Exception ex) {
                System.out.println("Error al insertar un periodista");
            }
        }
    }
    
    //Elimina un periodista de la BD. La entidad Periodista usa cascade para
    //todas las operaciones. Lo que significa, que cuando borramos a un periodista,
    // se eliminan también todos sus artículos.
    static void bajaPeriodista(PeriodistaDAO perDAO) {
        System.out.println("DNI a borrar:");
        String dni = new Scanner(System.in).nextLine();
        
        try {
            perDAO.destroy(dni); 
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el periodista");
        }
    }

    private static void nuevoArticulo(PeriodistaDAO perDAO, ArticuloDAO artDAO) {
        System.out.println("Datos del artículo");
        System.out.println("Título:");
        String titulo = new Scanner(System.in).nextLine();
        System.out.println("Anyo:");
        int anyo = new Scanner(System.in).nextInt();
        System.out.println("Número palabras:");
        int numPal = new Scanner(System.in).nextInt();

        //creamos el artículo
        Articulo a = new Articulo(titulo, anyo, numPal);

        System.out.println("DNI del periodista (autor): ");
        String dni = new Scanner(System.in).nextLine();

        Periodista p = perDAO.findPeriodista(dni); //buscamos al periodista

        if (p == null) {
            System.out.println("Lo sentimos, el periodista no existe.");
        } else {

            p.addArticulo(a); //anyadimos el nuevo artículo a la lista de su autor    

            //ahora podemos modificar el periodista
            try {
                perDAO.edit(p);
            } catch (Exception ex) {
                System.out.println("Error al modificar un periodista.");
            }

            // al persistir o modificar el periodista se persiste en cascada         
            //todos sus artículos. No hace falta hacer persistir el artículo.
        }
    }

    //Muestra todos los artículos escritos por un periodista.
    private static void articulosPeriodista(PeriodistaDAO perDAO) {
        System.out.println("DNI del periodista:");
        String dni = new Scanner(System.in).nextLine();

        Periodista p = perDAO.findPeriodista(dni);
        if (p == null) {
            System.out.println("Lo sentimos, el periodista no existe");
        } else {
            List<Articulo> lista = p.getArticulos(); //artículos de este periodista

            for (Articulo a : lista) {
                System.out.println(a);
            }
        }
    }

    //Realiza una búsqueda de todos los artículos escritos en un anyo.
    private static void articulosAnyo(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT a FROM Articulo a WHERE a.anyo = :anyo";
        Query query = em.createQuery(jpql);
        System.out.println("Artículos de qué anyo:");
        int anyo = new Scanner(System.in).nextInt();
        query.setParameter("anyo", anyo);
        
        List<Articulo> articulos = query.getResultList();
       
        for(Articulo a: articulos) {
            System.out.println(a);
        }
    }   
}
