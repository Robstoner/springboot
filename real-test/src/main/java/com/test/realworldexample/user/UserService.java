package com.test.realworldexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.realworldexample.exceptions.ItemNotFoundException;
import com.test.realworldexample.files.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
    }

    public byte[] getUserAvatar(String id) {
        User user = getUser(id);

        try {
            return fileService.load(user.getAvatarUrl()).getContentAsByteArray();
        } catch (Exception e) {
            throw new ItemNotFoundException("User avatar not found or corrupted");
        }
    }

    public User addUser(User entity) {
        return addUser(entity, null);
    }

    public User addUser(User entity, MultipartFile file) {
        if (file != null) {
            String fileName = fileService.save(file, "users");
            entity.setAvatarUrl(fileName);
        }

        entity.setRoles(new Role[] { Role.USER });
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        userRepository.save(entity);

        return entity;
    }

    public User editUser(String id, User entity) {
        return editUser(id, entity, null);
    }

    public User editUser(String id, User entity, MultipartFile file) {
        User user = getUser(id);

        if (entity == null) {
            return user;
        }

        if (entity.getFirstName() != null) {
            user.setFirstName(entity.getFirstName());
        }

        if (entity.getLastName() != null) {
            user.setLastName(entity.getLastName());
        }

        if (entity.getEmail() != null) {
            user.setEmail(entity.getEmail());
        }

        if (entity.getAddress() != null) {
            user.setAddress(entity.getAddress());
        }

        if (entity.getRoles() != null) {
            user.setRoles(entity.getRoles());
        }

        if (file != null) {
            if (user.getAvatarUrl() != null) {
                fileService.delete(user.getAvatarUrl());
            }

            String fileName = fileService.save(file, "users");
            user.setAvatarUrl(fileName);
        }

        userRepository.save(user);

        return user;
    }

    public void changePassword(String id, String password) {
        User user = getUser(id);

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User changeRoles(String id, Role[] roles) {
        User user = getUser(id);

        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

    public void deleteUser(String id) {
        User user = getUser(id);

        userRepository.delete(user);
    }
}
