package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.model.User;
import com.karol.hotelreservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Account with this email not found"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
