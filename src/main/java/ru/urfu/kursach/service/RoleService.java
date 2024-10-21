package ru.urfu.kursach.service;

import org.springframework.stereotype.Service;
import ru.urfu.kursach.entity.Role;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public interface RoleService {
    public static final byte MIN_RIGHTS = Byte.MIN_VALUE;
    public static final byte MAX_RIGHTS = Byte.MAX_VALUE;

    static final Map<Byte, String> roleNames = Stream.of(new Object[][] {
            { MIN_RIGHTS, "READ_ONLY" },
            { (byte)(MIN_RIGHTS + 1), "USER" },
            { MAX_RIGHTS, "ADMIN" },
    }).collect(Collectors.toMap(x -> (Byte)x[0], x -> (String)x[1]));

    static String getRoleNameByRights(byte rights) {
        return roleNames.get(rights > 1 ? (byte)MAX_RIGHTS : rights);
    }

    public boolean isAdminExists();
    public Role getOrCreateRoleByRights(byte rights);
    public Optional<Role> changeRole(byte rights, boolean isPromotion);

}
