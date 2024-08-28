package com.espe.oauth.oauthserver.oauth;

import com.espe.oauth.oauthserver.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    @Autowired
    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = "http://msvc-usuarios:8001/api/usuarios/nombre/" + username;
        Usuario usuario = restTemplate.getForObject(url, Usuario.class);

        if (usuario == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(usuario.getNombre())
                .password(usuario.getPassword())
                .roles("USER") // Adjust roles as needed
                .build();
    }
}