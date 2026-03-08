package com.logistica.entregas.repository;

import com.logistica.entregas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario,Long>{

    public Usuario findByLogin(String login);
}

