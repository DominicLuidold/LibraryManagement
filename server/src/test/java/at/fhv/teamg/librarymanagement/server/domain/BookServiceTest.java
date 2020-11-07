package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class BookServiceTest {

    @Test
    void search_shouldReturnDtoList() {
        // Mock incoming DTO
        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getTitle()).thenReturn("Title");
        when(bookDtoMock.getAuthor()).thenReturn("Author");
        when(bookDtoMock.getIsbn13()).thenReturn("ISBN-13");
        when(bookDtoMock.getTopic()).thenReturn("Topic");

        // Mock returned entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getTitle()).thenReturn("Title");
        when(mediumMock.getStorageLocation()).thenReturn("Storage Location");

        Book bookMock = mock(Book.class);
        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(bookMock.getId()).thenReturn(UUID.randomUUID());
        when(bookMock.getAuthor()).thenReturn("Author");

        List<Book> mockedEntities = new LinkedList<>();
        mockedEntities.add(bookMock);

        // Prepare BookService
        BookService bookService = new BookService();
        BookDao mockedDao = spy(BookDao.class);
        doReturn(mockedEntities).when(mockedDao)
            .findBy(anyString(), anyString(), anyString(), anyString());
        bookService.bookDao = mockedDao;

        // Assertions
        List<BookDto> result = bookService.search(bookDtoMock);

        assertFalse(result.isEmpty(), "Search result should not be an empty list");
        assertEquals(result.size(), 1, "Search result should contain one entry");
    }
}
