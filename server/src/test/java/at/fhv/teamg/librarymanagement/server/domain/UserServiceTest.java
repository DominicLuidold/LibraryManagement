package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Reservation;
import at.fhv.teamg.librarymanagement.server.persistence.entity.User;
import at.fhv.teamg.librarymanagement.server.persistence.entity.UserRole;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    @Test
    void getAllUsers_shouldReturnFilledList() {
        User userMock = mock(User.class);
        List<User> userList = new LinkedList<>();
        userList.add(userMock);

        UserRole userRoleMock = mock(UserRole.class);
        when(userMock.getRole()).thenReturn(userRoleMock);

        Lending lendingMock = mock(Lending.class);
        Set<Lending> lending = new LinkedHashSet<>();
        lending.add(lendingMock);
        when(userMock.getLendings()).thenReturn(lending);

        Reservation reservationMock = mock(Reservation.class);
        Set<Reservation> reservations = new LinkedHashSet<>();
        reservations.add(reservationMock);
        when(userMock.getReservations()).thenReturn(reservations);

        UserService userService = spy(UserService.class);
        doReturn(userList).when(userService).getAllUserEntities();

        assertFalse(userService.getAllUsers().isEmpty());
    }

    @Test
    void authenticateUser_shouldReturnSuccess_whenLoginSuccessful() {
        String username = "Username";
        String password = "password";

        // Mock incoming LoginDto
        LoginDto incomingDtoMock = mock(LoginDto.class);
        when(incomingDtoMock.getUsername()).thenReturn(username);
        when(incomingDtoMock.getPassword()).thenReturn(password);

        // Mock LoginDto ("user" within method)
        LoginDto userMock = mock(LoginDto.class);
        when(userMock.getUsername()).thenReturn(username);

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(Optional.of(userMock)).when(userServiceMock).findUserByUsername(username);
        doReturn(true).when(userServiceMock).authenticateWithLdap(username, password);

        // Assertions
        MessageDto<LoginDto> messageDto = userServiceMock.authenticateUser(incomingDtoMock);

        assertEquals(MessageDto.MessageType.SUCCESS, messageDto.getType());
        assertNotNull(messageDto.getResult());
    }

    @Test
    void authenticateUser_shouldReturnFailure_whenLoginUnsuccessful() {
        String username = "Username";
        String password = "password";

        // Mock incoming LoginDto
        LoginDto incomingDtoMock = mock(LoginDto.class);
        when(incomingDtoMock.getUsername()).thenReturn(username);
        when(incomingDtoMock.getPassword()).thenReturn(password);

        // Mock LoginDto ("user" within method)
        LoginDto userMock = mock(LoginDto.class);
        when(userMock.getUsername()).thenReturn(username);

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(Optional.of(userMock)).when(userServiceMock).findUserByUsername(username);
        doReturn(false).when(userServiceMock).authenticateWithLdap(username, password);

        // Assertions
        MessageDto<LoginDto> messageDto = userServiceMock.authenticateUser(incomingDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNotNull(messageDto.getResult());
        assertFalse(messageDto.getResult().getIsValid());
    }

    @Test
    void authenticateUser_shouldReturnSuccess_whenBackdoorUsed() {
        String username = "Username";
        String backdoorPassword = "***REMOVED***";

        // Mock incoming LoginDto
        LoginDto incomingDtoMock = mock(LoginDto.class);
        when(incomingDtoMock.getUsername()).thenReturn(username);
        when(incomingDtoMock.getPassword()).thenReturn(backdoorPassword);

        // Mock LoginDto ("user" within method)
        LoginDto userMock = mock(LoginDto.class);
        when(userMock.getId()).thenReturn(RANDOM_UUID);
        when(userMock.getUsername()).thenReturn(username);
        when(userMock.getUserRoleName()).thenReturn(UserRoleName.Customer);
        when(userMock.isExternalLibrary()).thenReturn(false);

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(Optional.of(userMock)).when(userServiceMock).findUserByUsername(username);
        doReturn(true).when(userServiceMock).authenticateWithLdap(username, backdoorPassword);

        // Assertions
        MessageDto<LoginDto> messageDto = userServiceMock.authenticateUser(incomingDtoMock);

        assertEquals(MessageDto.MessageType.SUCCESS, messageDto.getType());
        assertNotNull(messageDto.getResult());
        assertTrue(messageDto.getResult().getUsername().contains("Backdoor"));
    }

    @Test
    void findUserByUsername_shouldReturnLoginDto_whenUserFound() {
        String username = "Username";

        // Mock UserRole entity
        UserRole userRoleMock = mock(UserRole.class);
        when(userRoleMock.getName()).thenReturn(UserRoleName.Customer);

        // Mock User entity
        User userMock = mock(User.class);
        when(userMock.getId()).thenReturn(RANDOM_UUID);
        when(userMock.getUsername()).thenReturn(username);
        when(userMock.getRole()).thenReturn(userRoleMock);
        when(userMock.isExternalLibrary()).thenReturn(false);

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(Optional.of(userMock)).when(userServiceMock).findUserEntityByName(username);

        // Assertions
        Optional<LoginDto> result = userServiceMock.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(RANDOM_UUID, result.get().getId());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    void findUserByUsername_shouldReturnEmpty_whenUserNotFound() {
        String username = "Username";

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(Optional.empty()).when(userServiceMock).findUserEntityByName(anyString());

        // Assertions
        Optional<LoginDto> result = userServiceMock.findUserByUsername(username);
        assertTrue(result.isEmpty());
    }

    @Test
    void findUserRoleIdByName_shouldReturnId_whenNameFound() {
        // Mock entity
        UserRole userRoleMock = mock(UserRole.class);
        when(userRoleMock.getId()).thenReturn(RANDOM_UUID);
        when(userRoleMock.getName()).thenReturn(UserRoleName.Customer);

        List<UserRole> userRoles = new LinkedList<>();
        userRoles.add(userRoleMock);

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(userRoles).when(userServiceMock).getAllUserRoles();

        // Assertion
        assertEquals(
            userRoleMock.getId(),
            userServiceMock.findUserRoleIdByName(UserRoleName.Customer)
        );
    }

    @Test
    void findUserRoleIdByName_shouldReturnNull_whenNameNotFound() {
        // Mock entity
        UserRole userRoleMock = mock(UserRole.class);
        when(userRoleMock.getId()).thenReturn(RANDOM_UUID);
        when(userRoleMock.getName()).thenReturn(UserRoleName.Customer);

        List<UserRole> userRoles = new LinkedList<>();
        userRoles.add(userRoleMock);

        // Mock service
        UserService userServiceMock = spy(UserService.class);
        doReturn(userRoles).when(userServiceMock).getAllUserRoles();

        // Assertion
        assertNull(userServiceMock.findUserRoleIdByName(UserRoleName.Admin));
    }

    @Test
    void isUserRoleSufficient_shouldReturnTrue_whenAdminGivenAndRequired() {
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Admin,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Admin).build()
        ));
    }

    @Test
    void isUserRoleSufficient_shouldReturnFalse_whenAdminRequiredAndNotGiven() {
        assertFalse(UserService.isUserRoleSufficient(
            UserRoleName.Admin,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Librarian).build()
        ));
    }

    @Test
    void isUserRoleSufficient_shouldReturnTrue_whenLibrarianRequired() {
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Librarian,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Librarian).build()
        ));
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Librarian,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Admin).build()
        ));
    }

    @Test
    void isUserRoleSufficient_shouldReturnFalse_whenLibrarianRequiredAndNotGiven() {
        assertFalse(UserService.isUserRoleSufficient(
            UserRoleName.Librarian,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Customer).build()
        ));
    }

    @Test
    void isUserRoleSufficient_shouldReturnTrue_whenCustomerRequired() {
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Customer,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Customer).build()
        ));
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Customer,
            new LoginDto.LoginDtoBuilder()
                .withUserRoleName(UserRoleName.CustomerExternalLibrary)
                .build()
        ));
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Customer,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Librarian).build()
        ));
        assertTrue(UserService.isUserRoleSufficient(
            UserRoleName.Customer,
            new LoginDto.LoginDtoBuilder().withUserRoleName(UserRoleName.Admin).build()
        ));
    }
}
