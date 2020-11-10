package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.UserDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserService {
    /**
     * Get all Users.
     *
     * @return List of all Users
     */
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new LinkedList<>();

        getAll().forEach(user -> {
            UserDto.UserDtoBuilder builder = new UserDto.UserDtoBuilder(user.getId());
            builder.address(user.getAddress())
                .email(user.getEmail())
                .externalLibrary(user.isExternalLibrary())
                .name(user.getName())
                .phone(user.getPhone())
                .roleId(user.getRole().getId())
                .username(user.getUsername());

            Set<UUID> lendings = new LinkedHashSet<>();
            user.getLendings().forEach(lending -> {
                lendings.add(lending.getId());
            });
            builder.lendings(lendings);

            Set<UUID> reservations = new LinkedHashSet<>();
            user.getReservations().forEach(reservation -> {
                reservations.add(reservation.getId());
            });
            builder.reservations(reservations);

            userDtos.add(builder.build());
        });

        return userDtos;
    }

    protected List<User> getAll() {
        UserDao dao = new UserDao();
        return dao.getAll();
    }
}
