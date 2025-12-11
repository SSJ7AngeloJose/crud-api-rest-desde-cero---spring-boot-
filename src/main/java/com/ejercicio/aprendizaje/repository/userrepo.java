package com.ejercicio.aprendizaje.repository;

import com.ejercicio.aprendizaje.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userrepo extends JpaRepository <Usuario, Long> {

    public Usuario findByCorreo(String correo);



}
