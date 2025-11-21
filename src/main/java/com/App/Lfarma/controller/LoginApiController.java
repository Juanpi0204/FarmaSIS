package com.App.Lfarma.controller;

import com.App.Lfarma.entity.Usuario;
import com.App.Lfarma.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginApiController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public String login(@RequestBody Usuario user) {

        // Buscar usuario por username
        Usuario u = usuarioRepository.findByUsername(user.getUsername()).orElse(null);

        if (u == null) {
            return "ERROR_USER"; // usuario no existe
        }

        // Comparar contraseña SIN bcrypt
        if (u.getPassword().equals(user.getPassword())) {
            return "OK"; // login exitoso
        }

        return "ERROR_PASSWORD"; // contraseña incorrecta
    }
}
