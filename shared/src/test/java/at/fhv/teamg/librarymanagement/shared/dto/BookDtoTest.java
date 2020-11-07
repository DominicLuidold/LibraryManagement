package at.fhv.teamg.librarymanagement.shared.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class BookDtoTest {

    @Test
    void bookDtoBuilder_shouldReturnValidDto() {
        UUID id = UUID.fromString("2401e527-4bd3-4b67-8641-21119a686ed1");
        String title = "Corpus Delicti";
        String topic = "Not so good";
        List<String> tags = new LinkedList<>();
        tags.add("Test");
        String author = "Juli Zeh";
        String isbn10 = "3442740665";
        String isbn13 = "978-3442740666";
        String languageKey = "de-DE";
        String publisher = "Schöffling & Co.";
        LocalDate releaseDate = LocalDate.parse("2009-02-20");
        String storageLocation = "B-06";

        BookDto bookDto = new BookDto.BookDtoBuilder(id)
            .title(title)
            .topic(topic)
            .tags(tags)
            .author(author)
            .isbn10(isbn10)
            .isbn13(isbn13)
            .languageKey(languageKey)
            .publisher(publisher)
            .releaseDate(releaseDate)
            .storageLocation(storageLocation)
            .build();

        assertSame(bookDto.getId(), id, "UUID should be the same");
        assertSame(bookDto.getTitle(), title, "title should be the same");
        assertSame(bookDto.getTopic(), topic, "topic should be the same");
        assertEquals(bookDto.getTags(), tags, "tags should be the same");
        assertSame(bookDto.getAuthor(), author, "author should be the same");
        assertSame(bookDto.getIsbn10(), isbn10, "isbn10 should be the same");
        assertSame(bookDto.getIsbn13(), isbn13, "isbn13 should be the same");
        assertSame(bookDto.getLanguageKey(), languageKey, "languageKey should be the same");
        assertSame(bookDto.getPublisher(), publisher, "publisher should be the same");
        assertSame(bookDto.getReleaseDate(), releaseDate, "releaseDate should be the same");
        assertSame(
            bookDto.getStorageLocation(),
            storageLocation,
            "storageLocation should be the same"
        );
    }
}
