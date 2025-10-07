package com.biblioteca.model;

import javax.persistence.*;

// Importante el entity manager para la persistencia de datos es lo que en una bade de datos relacional seria el nombre de la tabla
@Entity
public class Libro {
    @Id // Id representa la clave primaria PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos de la clase Libro
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private int cantidad;

    // Constructor vacío requerido por JPA
    public Libro() {
    }

    // Constructor con parámetros (No tiene ID porque se genera automáticamente)
    public Libro(String titulo, String autor, String genero, int cantidad) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // No lo vamos a usar, pero es útil para depurar
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
