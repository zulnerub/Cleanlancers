package com.simo.web.user;

import com.simo.web.user.model.UserEntity;
import com.simo.web.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findUserById(String username){
        return this.userRepository.findOneByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findOneByEmail(username);

        LOGGER.debug("Trying to load user {}. Successful? {}", username, userOpt.isPresent());

        return userOpt
                .map(this::map).orElseThrow(() -> new UsernameNotFoundException("No such user " + username));
    }

    private User map(UserEntity user){
        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());

        User result = new User(
                user.getEmail(),
                user.getPasswordHash() != null ? user.getPasswordHash() : "",
                authorities);

        if (user.getPasswordHash() == null){
            result.eraseCredentials();
        }

        return result;
    }


}
