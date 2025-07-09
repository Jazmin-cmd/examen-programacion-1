package com.example.demo.controller;
import org.springframework.ui.Model; //importante para usar addAttributte
import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Mostrar lista de categorías
    @GetMapping("/categorias")
    public String verCategorias(Model model) {
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        model.addAttribute("categorias", listaCategorias);
        model.addAttribute("nuevaCategoria", new Categoria()); // para el modal
        return "categorias"; // nombre del archivo Thymeleaf: categorias.html
    }

    //Guardar nueva categoría (desde el modal)
    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    // Editar categoría: mostrar formulario
    @GetMapping("/categorias/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("categoria", categoria);
        return "form_categoria"; // archivo form_categoria.html
    }

    // Guardar cambios de edición
    @PostMapping("/categorias/editar")
    public String guardarEdicion(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    // Eliminar categoría
    @PostMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
}


