package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class BookServiceTest {
    private static final UUID validMediumId = UUID.randomUUID();
    private static final UUID notValidMediumId = UUID.randomUUID();
    private static final UUID validMediumCopyId = UUID.randomUUID();
    private static final UUID notValidMediumCopyId = UUID.randomUUID();

    @Test
    void search_shouldReturnDtoList() {
        // Mock incoming DTO
        BookDto bookDtoMock = mock(BookDto.class);
        UUID topic = UUID.fromString("2401e527-4bd3-4b67-8641-deadbeef6ed1");
        when(bookDtoMock.getTitle()).thenReturn("Title");
        when(bookDtoMock.getAuthor()).thenReturn("Author");
        when(bookDtoMock.getIsbn13()).thenReturn("ISBN-13");
        when(bookDtoMock.getTopic()).thenReturn(topic);

        // Mock returned entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getTitle()).thenReturn("Title");
        when(mediumMock.getStorageLocation()).thenReturn("Storage Location");

        Topic topicEntityMock = mock(Topic.class);
        when(mediumMock.getTopic()).thenReturn(topicEntityMock);

        Book bookMock = mock(Book.class);
        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(bookMock.getId()).thenReturn(UUID.randomUUID());
        when(bookMock.getAuthor()).thenReturn("Author");

        List<Book> mockedEntities = new LinkedList<>();
        mockedEntities.add(bookMock);

        // Prepare BookService
        BookService bookService = spy(BookService.class);
        Topic topicMock = mock(Topic.class);
        when(topicMock.getName()).thenReturn("Topic");
        doReturn(mockedEntities).when(bookService)
            .findBy(anyString(), anyString(), anyString(), anyString());
        doReturn(Optional.of(topicMock)).when(bookService).findTopicById(any(UUID.class));

        // Assertions
        List<BookDto> result = bookService.search(bookDtoMock);

        assertFalse(result.isEmpty(), "Search result should not be an empty list");
        assertEquals(result.size(), 1, "Search result should contain one entry");
    }

    @Test
    void getAllBooks_shouldReturnList() {
        Book bookMock = mock(Book.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);

        List<Book> bookList = new LinkedList<>();
        bookList.add(bookMock);

        BookService bookService = spy(BookService.class);
        doReturn(bookList).when(bookService).getAll();

        assertFalse(bookService.getAllBooks().isEmpty());
    }

    @Test
    void getBookByMediumId_shouldReturnDto() {
        Book bookMock = mock(Book.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);
        when(mediumMock.getBook()).thenReturn(bookMock);

        BookService bookService = spy(BookService.class);
        doReturn(Optional.of(mediumMock)).when(bookService).findMediumById(validMediumId);

        assertTrue(bookService.getBookByMediumId(validMediumId).isPresent());
    }

    @Test
    void getBookByMediumId_shouldReturnEmpty() {
        BookService bookService = spy(BookService.class);
        doReturn(Optional.empty()).when(bookService).findMediumById(notValidMediumId);
        assertFalse(bookService.getBookByMediumId(notValidMediumId).isPresent());
    }

    @Test
    void getBookByMediumCopyId_shouldReturnDto() {
        Book bookMock = mock(Book.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);
        when(mediumMock.getBook()).thenReturn(bookMock);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);

        BookService bookService = spy(BookService.class);
        doReturn(Optional.of(mediumCopyMock)).when(bookService)
            .findMediumCopyById(validMediumCopyId);

        assertTrue(bookService.getBookByMediumCopyId(validMediumCopyId).isPresent());
    }

    @Test
    void getBookByMediumCopyId_shouldReturnEmpty() {
        BookService bookService = spy(BookService.class);
        doReturn(Optional.empty()).when(bookService).findMediumCopyById(notValidMediumCopyId);
        assertFalse(bookService.getBookByMediumCopyId(notValidMediumCopyId).isPresent());
    }
}
