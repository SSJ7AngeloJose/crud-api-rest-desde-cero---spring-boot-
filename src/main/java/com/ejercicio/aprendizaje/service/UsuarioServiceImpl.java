package com.ejercicio.aprendizaje.service;

import com.ejercicio.aprendizaje.repository.userrepo;
import com.ejercicio.aprendizaje.user.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServiceImpl {

    @Autowired
    userrepo userrepo;

    //listar usuarios
    public List<Usuario> listarusuarios(){
        return userrepo.findAll();
    }

    //guardar usuario
    public Usuario saveUsuario(@Valid Usuario usuario){
        return userrepo.save(usuario);
    }

    //editar usuario
    public Usuario editusuario(Long id, @Valid  Usuario usuario){
         Usuario usuarioBD = userrepo.findById(id).orElse(null);
         if(usuarioBD == null){
             //control de exceptions
             throw new UsuarionoencontradoException("Usuario con id" + id + " no existe");
            //return  null;
         }
         else {
             usuarioBD.setNombre(usuario.getNombre());
             usuarioBD.setApellido(usuario.getApellido());
             usuarioBD.setCorreo(usuario.getCorreo());
         }
         return  userrepo.save(usuarioBD);
    }

    //eliminar usuario
    public void eliminarusuario(Long id){
        userrepo.deleteById(id);
    }
    //control de exceptions
    public class UsuarionoencontradoException extends RuntimeException {
        public UsuarionoencontradoException(String mensaje) {
            super(mensaje);
        }
    }

    //control de exceptions
    @RestControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(UsuarionoencontradoException.class)
        public ResponseEntity<String> manejarUsuarioNoEncontrado(UsuarionoencontradoException ex) {
            // Esto devuelve HTTP 404 con tu mensaje personalizado
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //VALIDACION DE CAMPOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

/*
1. ¿Qué son los códigos HTTP?
 Son mensajes del servidor que le dicen al cliente si la operación salió bien o mal.
 Los más importantes son:

✔️ 2xx – Éxito

200 OK → Todo salió bien (listar, actualizar)

201 CREATED → Se creó un recurso (registrar)

✔️ 4xx – Error del cliente

400 Bad Request → Datos incorrectos / faltan campos

401 Unauthorized → No autenticado

403 Forbidden → No autorizado

404 Not Found → El recurso no existe

409 Conflict → Conflicto (por ejemplo, correo duplicado)

✔️ 5xx – Error del servidor

500 Internal Server Error → Fallo inesperado en el backend

503 Service Unavailable → Servicio caído

*/







}
