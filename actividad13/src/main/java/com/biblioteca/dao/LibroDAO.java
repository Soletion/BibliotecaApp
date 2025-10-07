package com.biblioteca.dao;

import com.biblioteca.model.Libro;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

public class LibroDAO {
    // EntityManagerFactory es la fábrica de EntityManager, que es la interfaz principal para interactuar con la base de datos
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");

    // Agregar un libro a la base de datos
    public void agregarLibro(Libro libro) {
        // Crear un EntityManager para gestionar la persistencia y transacciones con la base de datos
        EntityManager em = emf.createEntityManager();
        // Siempre en try catch para manejar excepciones, y para que en el finally se cierre el EntityManager por tanto la conexión a la base de datos
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error al agregar libro: " + e.getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // Eliminar un libro de la base de datos
    public void eliminarLibro(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, id);
            if (libro != null) {
                em.remove(libro);
            } else {
                System.err.println("Libro no encontrado para eliminar (ID: " + id + ")");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // Modificar un libro existente en la base de datos (le puedo pasar el libro directamente sin tener que construirlo de nuevo)
    public void modificarLibro(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error al modificar libro: " + e.getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    // Obtengo todos los libros de la base de datos
    public List<Libro> obtenerTodos() {
        EntityManager em = emf.createEntityManager();
        List<Libro> libros = new ArrayList<>();
        try {
            // Esta consulta hace un array de libros donde l es un alias de la tabla Libro (Que se llama asi por el objeto Libro) Libro.class lo que hace es decirle que la consulta va a devolver una lista de objetos de tipo Libro
            libros = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener libros: " + e.getMessage());
        } finally {
            em.close();
        }
        return libros;
    }

    // Buscar libros por título, autor o género (puedo buscar por cualquiera de los 3)
    public List<Libro> buscarPorTitulo(String titulo) {
        EntityManager em = emf.createEntityManager();
        List<Libro> libros = new ArrayList<>();
        try {
            /* IMP! comentario para entender la consulta en los demas no lo pongo
            * La consulta busca libros cuyo título contenga la cadena proporcionada (sin importar mayúsculas o minúsculas)
            * Se usa el operador LIKE para buscar coincidencias parciales
            * Se usa LOWER para hacer la búsqueda insensible a mayúsculas/minúsculas
            * Se usa el % para buscar coincidencias parciales
            * Se usa el : para indicar que es un parámetro de la consulta
            * Se usa el setParameter para establecer el valor del parámetro// IMP! comentario para entender la consulta en los demas no lo pongo
            * La consulta busca libros cuyo título contenga la cadena proporcionada (sin importar mayúsculas o minúsculas)
            * Se usa el operador LIKE para buscar coincidencias parciales
            * Se usa LOWER para hacer la búsqueda insensible a mayúsculas/minúsculas
            * Se usa el % para buscar coincidencias parciales
            * Se usa el : para indicar que es un parámetro de la consulta
            * Se usa el setParameter para establecer el valor del parámetro
            */
            libros = em.createQuery(
                    "SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE :titulo", Libro.class)
                    .setParameter("titulo", "%" + titulo.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar por título: " + e.getMessage());
        } finally {
            em.close();
        }
        return libros;
    }

    // Busco por autor y genero de la misma manera que por titulo
    public List<Libro> buscarPorAutor(String autor) {
        EntityManager em = emf.createEntityManager();
        List<Libro> libros = new ArrayList<>();
        try {
            libros = em.createQuery(
                    "SELECT l FROM Libro l WHERE LOWER(l.autor) LIKE :autor", Libro.class)
                    .setParameter("autor", "%" + autor.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar por autor: " + e.getMessage());
        } finally {
            em.close();
        }
        return libros;
    }

    // Busco por genero de la misma manera que por titulo
    public List<Libro> buscarPorGenero(String genero) {
        EntityManager em = emf.createEntityManager();
        List<Libro> libros = new ArrayList<>();
        try {
            libros = em.createQuery(
                    "SELECT l FROM Libro l WHERE LOWER(l.genero) LIKE :genero", Libro.class)
                    .setParameter("genero", "%" + genero.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar por género: " + e.getMessage());
        } finally {
            em.close();
        }
        return libros;
    }

    // Opcional: cerrar el EntityManagerFactory al terminar la aplicación
    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
