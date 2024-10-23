package ru.urfu.kursach.component;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.urfu.kursach.entity.User;
import ru.urfu.kursach.entity.UserSpecific;
import ru.urfu.kursach.exception.NoRightsException;
import ru.urfu.kursach.exception.NotFoundException;
import ru.urfu.kursach.repository.UserJpaRepository;
import ru.urfu.kursach.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserSpecificComponent {

    private final UserRepository userRepository;

    public UserSpecificComponent(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public <T extends UserSpecific, Y> T save(UserJpaRepository<T, Y> repository, T entity) {
        entity.setUser(getUser());
        return repository.save(entity);
    }

    public <T extends UserSpecific, Y> List<T> getAll(UserJpaRepository<T, Y> repository) {
        var user = getUser();
        var roles = user.getRoles();
        if (roles.stream().noneMatch(r -> Objects.equals(r.getName(), "ROLE_ADMIN"))
                && roles.stream().anyMatch(r -> Objects.equals(r.getName(), "ROLE_USER"))) {
            return repository.findByUserId(user.getId());
        } else {
            return repository.findAll();
        }
    }

    public <T extends UserSpecific> T check(Optional<T> value) throws Exception {
        var principal = getUser();
        Optional<T> res = Optional.empty();
        if (principal.getRoles().stream().noneMatch(r -> Objects.equals(r.getName(), "ROLE_USER"))
                || (principal.getRoles().stream().anyMatch(r -> Objects.equals(r.getName(), "ROLE_ADMIN")))
                || (value.isPresent() && Objects.equals(value.get().getUser().getId(), principal.getId()))) {
            res = value;
        }
        if (value.isPresent() && res.isEmpty()) {
            throw new NoRightsException(
                    value.get().getId(),
                    value.getClass().getGenericSuperclass().getTypeName()
            );
        } else if (res.isEmpty()) {
            throw new NotFoundException(value.getClass().getGenericSuperclass().getTypeName());
        }
        return res.get();
    }

    private User getUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
