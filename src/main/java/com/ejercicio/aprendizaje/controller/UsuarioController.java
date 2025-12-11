package com.ejercicio.aprendizaje.controller;

import com.ejercicio.aprendizaje.service.UsuarioServiceImpl;
import com.ejercicio.aprendizaje.user.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/listar")
    public  List<Usuario> listarusuarios(){
        return usuarioServiceImpl.listarusuarios();
    }

    @PostMapping("/guardar")
    public Usuario guardarUsario(@RequestBody Usuario usuario){
        return usuarioServiceImpl.saveUsuario(usuario);
    }

    @PutMapping("/editar/{id}")
    public Usuario editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
         return  usuarioServiceImpl.editusuario(id, usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarUsuario(@PathVariable Long id){
        usuarioServiceImpl.eliminarusuario(id);
    }







}
