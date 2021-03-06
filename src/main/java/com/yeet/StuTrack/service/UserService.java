package com.yeet.StuTrack.service;

import com.yeet.StuTrack.dao.UserRepository;
import com.yeet.StuTrack.model.entity.User;
import com.yeet.StuTrack.security.UserPrincipal;
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

    public Optional<User> userDetails(String username){
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public boolean userExist(String username){
        return userDetails(username).isPresent();
    }

    public Optional<User> createUser(String username, String password){
        return Optional.of(userRepository.save(new User(User.UserType.NORMAL, username, password)));
    }

    public Optional<User> createAdmin(String username, String password){
        return Optional.of(userRepository.save(new User(User.UserType.ADMIN, username, password)));
    }

    public UserDetails getUserDetailsById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UsernameNotFoundException("User not found!") );

        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDetails(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Login failed"));
        return UserPrincipal.create(user.get());
    }
}
