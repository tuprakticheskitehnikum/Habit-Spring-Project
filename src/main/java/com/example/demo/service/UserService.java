package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.EmailMismatchException;
import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.payload.request.UpgradeRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void upgradeUser(UpgradeRequest upgradeRequest) {
        User user = getUser(upgradeRequest);
        ensureEmailsMatch(upgradeRequest, user);

        assignRoles(upgradeRequest.getRoles(), user);

        userRepository.save(user);
    }

    private User getUser(UpgradeRequest upgradeRequest) {
        return userRepository.findByUsername(upgradeRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("There is no user with username: " +
                        upgradeRequest.getUsername()));
    }

    private void ensureEmailsMatch(UpgradeRequest upgradeRequest, User user) {
        if(!user.getEmail().equals(upgradeRequest.getEmail())){
            throw new EmailMismatchException("Wrong username or email");
        }
    }

    private void assignRoles(Set<String> strRoles, User user) {
        Set<Role> roles = mapRoles(strRoles);

        user.setRoles(roles);
    }

    private Set<Role> mapRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (isNull(strRoles)) {
            roles.add(getRole(ERole.ROLE_CLIENT));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(getRole(ERole.ROLE_ADMIN));
                        break;
                    default:
                        roles.add(getRole(ERole.ROLE_CLIENT));
                }
            });
        }
        return roles;
    }

    private boolean isNull(Set<String> strRoles) {
        return strRoles == null;
    }

    private Role getRole(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
    }
}
