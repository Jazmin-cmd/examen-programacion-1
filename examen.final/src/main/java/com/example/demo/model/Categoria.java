package com.example.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "categoria")

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    private String nombre;

    // Opcional: relación inversa con Producto
    // @OneToMany(mappedBy = "categoria")
    // private Set<Producto> productos;

    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}