package com.keidi.application.UserConfig;

import com.keidi.application.Entity.Usuario;
import com.keidi.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}