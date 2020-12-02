package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Reservation;
import at.fhv.teamg.librarymanagement.server.persistence.entity.User;
import at.fhv.teamg.librarymanagement.server.persistence.entity.UserRole;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    @Test
    void getAllUsers_shouldReturnFilledList() {
        User userMock = mock(User.class);
        List<User> userList = new LinkedList<>();
        userList.add(userMock);

        UserRole userRoleMock = mock(UserRole.class);
        when(userMock.getRole()).thenReturn(userRoleMock);

        Lending lendingMock = mock(Lending.class);
        Set<Lending> lendings = new LinkedHashSet<>();
        lendings.add(lendingMock);
        when(userMock.getLendings()).thenReturn(lendings);

        Reservation reservationMock = mock(Reservation.class);
        Set<Reservation> reservations = new LinkedHashSet<>();
        reservations.add(reservationMock);
        when(userMock.getReservations()).thenReturn(reservations);

        UserService userService = spy(UserService.class);
        doReturn(userList).when(userService).getAll();

        assertFalse(userService.getAllUsers().isEmpty());
    }
}
