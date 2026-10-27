package com.expense.backend.service;


import com.expense.backend.dto.*;
import com.expense.backend.entity.User;
import com.expense.backend.enums.Provider;
import com.expense.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileResponse getProfile(User user) {
        boolean canChangePassword = user.getProvider() == Provider.LOCAL;
        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getProvider().name(),
                user.getCreatedAt(),
                canChangePassword
        );
    }

    public ProfileResponse updateProfile(UpdateProfileRequest request, User user) {
        user.setName(request.getName());
        userRepository.save(user);

        return getProfile(user);
    }

    public void changePassword(ChangePasswordRequest request, User user) {
        if (user.getProvider() == Provider.GOOGLE) {
            throw new RuntimeException("Google users cannot change password");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new RuntimeException("New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
