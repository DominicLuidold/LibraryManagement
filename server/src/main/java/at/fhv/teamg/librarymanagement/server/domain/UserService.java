package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.domain.common.Utils;
import at.fhv.teamg.librarymanagement.server.ldap.LdapConnector;
import at.fhv.teamg.librarymanagement.server.persistance.dao.UserDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
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
        List<UserDto> userDtoList = new LinkedList<>();

        getAll().forEach(user -> {
            UserDto.UserDtoBuilder builder = new UserDto.UserDtoBuilder(user.getId());
            builder.address(user.getAddress())
                .email(user.getEmail())
                .externalLibrary(user.isExternalLibrary())
                .name(user.getName())
                .phone(user.getPhone())
                .roleId(user.getRole().getId())
                .username(user.getUsername());

            Set<UUID> lendingSet = new LinkedHashSet<>();
            user.getLendings().forEach(lending -> {
                lendingSet.add(lending.getId());
            });
            builder.lendings(lendingSet);

            Set<UUID> reservations = new LinkedHashSet<>();
            user.getReservations().forEach(reservation -> {
                reservations.add(reservation.getId());
            });
            builder.reservations(reservations);

            userDtoList.add(builder.build());
        });

        return userDtoList;
    }

    /**
     * Authenticates User against LDAP.
     *
     * @param loginUser user to login.
     * @return LoginDto with isValid set to true if login was Successfully.
     */
    public MessageDto<LoginDto> authenticateUser(LoginDto loginUser) {
        LOG.info("Try to Login user with Username: {}", loginUser.getUsername());

        // Special backdoor user required by porudct owner
        if (loginUser.getUsername().equals("backdoor") && loginUser.getPassword().equals("1234")) {
            return Utils.createMessageResponse(
                "User logged in successfully",
                MessageDto.MessageType.SUCCESS,
                new LoginDto.LoginDtoBuilder()
                    .withUsername("Backdoor")
                    .withUserRoleName(UserRoleName.Admin)
                    .withIsValid(true)
                    .build()
            );
        }

        boolean isValid = LdapConnector.authenticateJndi(
            loginUser.getUsername(),
            loginUser.getPassword()
        );

        LoginDto loggedInUser = new LoginDto.LoginDtoBuilder()
            .withUsername(loginUser.getUsername())
            .withIsValid(isValid)
            .build();

        if (isValid) {
            Optional<LoginDto> validLoginUser = findUserByUsername(loggedInUser.getUsername());
            if (validLoginUser.isPresent()) {
                return Utils.createMessageResponse(
                    "Login successful",
                    MessageDto.MessageType.SUCCESS,
                    validLoginUser.get()
                );
            }
        }

        LOG.info("Failed login of User: {}", loginUser.getUsername());
        return Utils.createMessageResponse(
            "Login failed",
            MessageDto.MessageType.FAILURE,
            loggedInUser
        );
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
                .withId(user.getId())
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
