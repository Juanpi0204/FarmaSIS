package com.App.Lfarma.controller;

import com.App.Lfarma.entity.Usuario;
import com.App.Lfarma.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*") // Permite que Flutter acceda desde cualquier origen
public class LoginApiController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario user) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Buscar usuario por username
            Usuario u = usuarioRepository.findByUsername(user.getUsername()).orElse(null);

            if (u == null) {
                response.put("success", false);
                response.put("message", "Usuario no existe");
                return ResponseEntity.ok(response);
            }

            if (u.getPassword() == null) {
                response.put("success", false);
                response.put("message", "Contraseña no registrada");
                return ResponseEntity.ok(response);
            }

            // Comparar contraseña encriptada
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("username", u.getUsername()); // opcional
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Contraseña incorrecta");
                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
