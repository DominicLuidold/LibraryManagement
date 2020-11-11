package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MediumCopyServiceTest {
    private static final UUID validId = UUID.fromString("16748d88-517f-4684-bb39-ef2fa1168d74");
    private static final UUID notValidId = UUID.fromString("B16B00B5-4711-1337-6969-BADBADBADBAD");

    @Test
    void getCopiesBook_shouldReturnFilledList() {
        Book bookMock = mock(Book.class);
        Medium mediumMock = mock(Medium.class);
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        HashSet<MediumCopy> copies = new HashSet<>();
        copies.add(mediumCopyMock);

        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getCopies()).thenReturn(copies);

        MediumCopyService mediumCopyService = spy(MediumCopyService.class);
        doReturn(Optional.of(bookMock)).when(mediumCopyService).findBookById(validId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(validId);

        assertFalse(mediumCopyService.getCopies(bookDtoMock).isEmpty());
    }

    @Test
    void getCopiesBook_shouldReturnEmptyList() {
        MediumCopyService mediumCopyService = spy(MediumCopyService.class);
        doReturn(Optional.empty()).when(mediumCopyService).findBookById(notValidId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(notValidId);

        assertTrue(mediumCopyService.getCopies(bookDtoMock).isEmpty());
    }

    @Test
    void getCopiesDvd_shouldReturnFilledList() {
        Dvd dvdMock = mock(Dvd.class);
        Medium mediumMock = mock(Medium.class);
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        HashSet<MediumCopy> copies = new HashSet<>();
        copies.add(mediumCopyMock);

        when(dvdMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getCopies()).thenReturn(copies);

        MediumCopyService mediumCopyService = spy(MediumCopyService.class);
        doReturn(Optional.of(dvdMock)).when(mediumCopyService).findDvdById(validId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(validId);

        assertFalse(mediumCopyService.getCopies(dvdDtoMock).isEmpty());
    }

    @Test
    void getCopiesDvd_shouldReturnEmptyList() {
        MediumCopyService mediumCopyService = spy(MediumCopyService.class);
        doReturn(Optional.empty()).when(mediumCopyService).findDvdById(notValidId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(notValidId);

        assertTrue(mediumCopyService.getCopies(dvdDtoMock).isEmpty());
    }


    @Test
    void getCopiesGame_shouldReturnFilledList() {
        Game gameMock = mock(Game.class);
        Medium mediumMock = mock(Medium.class);
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        HashSet<MediumCopy> copies = new HashSet<>();
        copies.add(mediumCopyMock);

        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getCopies()).thenReturn(copies);

        MediumCopyService mediumCopyService = spy(MediumCopyService.class);
        doReturn(Optional.of(gameMock)).when(mediumCopyService).findGameById(validId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(validId);

        assertFalse(mediumCopyService.getCopies(gameDtoMock).isEmpty());
    }

    @Test
    void getCopiesGame_shouldReturnEmptyList() {
        MediumCopyService mediumCopyService = spy(MediumCopyService.class);
        doReturn(Optional.empty()).when(mediumCopyService).findGameById(notValidId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(notValidId);

        assertTrue(mediumCopyService.getCopies(gameDtoMock).isEmpty());
    }
}
