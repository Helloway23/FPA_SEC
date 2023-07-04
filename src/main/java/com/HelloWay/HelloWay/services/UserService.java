package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.exception.ResourceNotFoundException;
import com.HelloWay.HelloWay.exception.UserNotFoundException;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.UserRepository;
import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    //    employee.setPassword(passwordEncoder.encode(employee.getPassword()));
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


    @Transactional
    public User loadUserByIdAndRole(Long userId, String role) throws NotFoundException {
        User user = UserRepo.findByIdAndRolesContaining(userId, role)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this id and role : " + role));

        return user;
    }

    public Page<User> getUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return UserRepo.findAll(pageable);
    }

}
