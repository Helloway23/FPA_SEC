package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.exception.UserNotFoundException;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {
    private static UserRepository UserRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository UserRepo, PasswordEncoder passwordEncoder) {
        this.UserRepo = UserRepo;

        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return UserRepo.save(employee);
    }

    public List<User> findAllUsers() {
        return UserRepo.findAll();
    }

    public User updateUser(User employee) {
        return UserRepo.save(employee);
    }

    public User findUserById(Long id) {
        return UserRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteUser(Long id) {
        UserRepo.deleteById(id);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}
