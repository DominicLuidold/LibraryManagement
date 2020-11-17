package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.ldap.LdapConnector;
import at.fhv.teamg.librarymanagement.server.persistance.dao.UserDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class);

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

    /**
     * Authenticate User against LDAP.
     * @param loginUser user to login.
     * @return LoginDto with isValid set to true if login was Successfully.
     */
    public LoginDto authenticateUser(LoginDto loginUser) {
        boolean isValid = false;

        LOG.info("Try to Login user with Username: {}", loginUser.getUsername());

        isValid = LdapConnector.authenticateJndi(
            loginUser.getUsername(),
            loginUser.getPassword()
        );

        LoginDto loggedInUser = new LoginDto.LoginDtoBuilder()
            .withUsername(loginUser.getUsername())
            .withIsValid(isValid)
            .build();

        if (!isValid) {
            LOG.info("Failed login of User: {}", loginUser.getUsername());
            return loggedInUser;
        } else {
            Optional<LoginDto> validLoginUser = findUserByUsername(loggedInUser.getUsername());
            if (validLoginUser.isPresent()) {
                return validLoginUser.get();
            }
            return loggedInUser;
        }
    }

    /**
     * Find a Topic by its ID.
     *
     * @param username to find User
     * @return LoginDto with all Parameters
     */
    public Optional<LoginDto> findUserByUsername(String username) {
        UserDao dao = new UserDao();
        Optional<User> foundUser = dao.findByName(username);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            LoginDto foundLoginDto = new LoginDto.LoginDtoBuilder()
                .withid(user.getId())
                .withUsername(user.getUsername())
                .withUserRoleName(user.getRole().getName())
                .withIsExternalLibrary(user.isExternalLibrary())
                .withIsValid(true)
                .build();
            return Optional.of(foundLoginDto);
        }
        return Optional.empty();
    }

    protected List<User> getAll() {
        UserDao dao = new UserDao();
        return dao.getAll();
    }
}
