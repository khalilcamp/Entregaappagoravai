package com.logistica.entregas.controller;

import com.logistica.entregas.config.JwtService;
import com.logistica.entregas.dto.LoginRequest;
import com.logistica.entregas.entity.Usuario;
import com.logistica.entregas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired JwtService jwtService;
    @Autowired UserRepository userRepository;
    @Autowired BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        Usuario usuario = userRepository.findByLogin(request.getLogin());
        if (usuario == null){
            throw new RuntimeException("Usuário não encontrado!");
        } else {
            request.setLogin(usuario.getLogin());
            String tentativaSenha = request.getSenha();
            String usuarioSenha = usuario.getSenha();
            if (passwordEncoder.matches(tentativaSenha,usuarioSenha)){
                request.setSenha(usuario.getSenha());
            } else {
                throw new RuntimeException("Senha Inválida");
            }

        }
        return jwtService.gerarToken(request.getLogin());
    }
    
    @PostMapping("/registro")
    public String registro(@RequestBody LoginRequest request){
        Usuario usuario = new Usuario();

        if (request.getLogin() == null || userRepository.findByLogin(request.getLogin()) != null) {
            throw new RuntimeException("Login inválido ou já cadastrado");
        }
        usuario.setLogin(request.getLogin());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        userRepository.save(usuario);

        return "Usuário cadastrado com sucesso!";
    }

}
