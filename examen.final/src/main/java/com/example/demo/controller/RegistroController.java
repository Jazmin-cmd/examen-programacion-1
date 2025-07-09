package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @Valid @ModelAttribute Usuario usuario,
            BindingResult bindingResult,
            Model model) {

        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            return "registro"; // vuelve a mostrar el formulario con mensajes
        }

        // Validar que no exista otro usuario con el mismo nombre
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            bindingResult.rejectValue("username", "error.usuario", "El nombre de usuario ya está en uso");
            return "registro";
        }

        // Encriptar la contraseña y guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER");
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }
}

