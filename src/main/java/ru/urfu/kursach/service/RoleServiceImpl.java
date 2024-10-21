package ru.urfu.kursach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.kursach.entity.Role;
import ru.urfu.kursach.repository.RoleRepository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoleServiceImpl implements RoleService {
    public final boolean adminExists;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        adminExists = checkAdmin();
    }

    @Override
    public boolean isAdminExists() {
        return adminExists;
    }

    @Override
    public Role getOrCreateRoleByRights(byte rights) {
        var name = "ROLE_" + RoleService.getRoleNameByRights(rights);
        var role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(Role.builder().name(name).build());
        }
        return role;
    }

    @Override
    public Optional<Role> changeRole(byte rights, boolean isPromotion) {
        return (rights == MAX_RIGHTS && isPromotion
                || rights == MIN_RIGHTS && !isPromotion)
                ? Optional.empty()
                : Optional.of(getOrCreateRoleByRights((byte) (isPromotion ? rights + 1 : rights - 1)));

    }

    private boolean checkAdmin() {
        var role = getOrCreateRoleByRights(MAX_RIGHTS);
        return role.getUsers() != null && !role.getUsers().isEmpty();
    }

    private Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }
}
