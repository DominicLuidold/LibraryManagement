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
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class DetailServiceTest {
    private static final UUID validId = UUID.fromString("16748d88-517f-4684-bb39-ef2fa1168d74");
    private static final UUID notValidId = UUID.fromString("B16B00B5-4711-1337-6969-BADBADBADBAD");

    @Test
    void getBookDetail_shouldReturnDto() {
        Book bookMock = mock(Book.class);
        Medium mediumMock = mock(Medium.class);
        when(bookMock.getMedium()).thenReturn(mediumMock);

        DetailService detailService = spy(DetailService.class);
        doReturn(Optional.of(bookMock)).when(detailService).findBookById(validId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(validId);

        assertTrue(detailService.getBookDetail(bookDtoMock).isPresent());
    }

    @Test
    void getBookDetail_shouldReturnEmpty() {
        DetailService detailService = spy(DetailService.class);
        doReturn(Optional.empty()).when(detailService).findBookById(notValidId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(notValidId);

        assertFalse(detailService.getBookDetail(bookDtoMock).isPresent());
    }

    @Test
    void getDvdDetail_shouldReturnDto() {
        Dvd dvdMock = mock(Dvd.class);
        Medium mediumMock = mock(Medium.class);
        when(dvdMock.getMedium()).thenReturn(mediumMock);

        DetailService detailService = spy(DetailService.class);
        doReturn(Optional.of(dvdMock)).when(detailService).findDvdById(validId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(validId);

        assertTrue(detailService.getDvdDetail(dvdDtoMock).isPresent());
    }

    @Test
    void getDvdDetail_shouldReturnEmpty() {
        DetailService detailService = spy(DetailService.class);
        doReturn(Optional.empty()).when(detailService).findDvdById(notValidId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(notValidId);

        assertFalse(detailService.getDvdDetail(dvdDtoMock).isPresent());
    }


    @Test
    void getGameDetail_shouldReturnDto() {
        Game gameMock = mock(Game.class);
        Medium mediumMock = mock(Medium.class);
        when(gameMock.getMedium()).thenReturn(mediumMock);

        DetailService detailService = spy(DetailService.class);
        doReturn(Optional.of(gameMock)).when(detailService).findGameById(validId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(validId);

        assertTrue(detailService.getGameDetail(gameDtoMock).isPresent());
    }

    @Test
    void getGameDetail_shouldReturnEmpty() {
        DetailService detailService = spy(DetailService.class);
        doReturn(Optional.empty()).when(detailService).findGameById(notValidId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(notValidId);

        assertFalse(detailService.getGameDetail(gameDtoMock).isPresent());
    }
}
