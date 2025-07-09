package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private CategoriaRepository categoriaRepo;
    //Esta seria una prueba para git
    @GetMapping("/productos")
    public String listarProductos(Model model) {
        List<Producto> productos = productoRepo.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convertir la lista de productos para agregar un campo con la fecha formateada
        List<Map<String, Object>> productosConFechaFormateada = productos.stream().map(p -> {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", p.getId());
            mapa.put("nombre", p.getNombre());
            mapa.put("precio", p.getPrecio());
            // Si la fecha es null, mostrar N/A
            String fechaFormateada = p.getFechaIngreso() != null ? p.getFechaIngreso().format(formatter) : "N/A";
            mapa.put("fechaIngresoFormateada", fechaFormateada);
            mapa.put("categoria", p.getCategoria());
            return mapa;
        }).collect(Collectors.toList());

        model.addAttribute("productos", productosConFechaFormateada);
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("producto", new Producto()); // Para el modal
        return "productos";
    }

    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepo.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepo.deleteById(id);
        return "redirect:/productos";
    }
}



