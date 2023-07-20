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
    private static UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository UserRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = UserRepo;

        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User employee) {
    //    employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return userRepo.save(employee);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User updatedUser) {
        User existingUser = userRepo.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            // Copy the properties from the updatedUser to the existingUser
            existingUser.setName(updatedUser.getName());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setBirthday(updatedUser.getBirthday());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());

            userRepo.save(existingUser);
            return existingUser;
        } else {
            // Handle the case where the user doesn't exist in the database
            // You may throw an exception or handle it based on your use case.
            return null;
        }
    }
    public User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }


    @Transactional
    public User loadUserByIdAndRole(Long userId, String role) throws NotFoundException {
        User user = userRepo.findByIdAndRolesContaining(userId, role)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this id and role : " + role));

        return user;
    }

    public Page<User> getUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepo.findAll(pageable);
    }

}
