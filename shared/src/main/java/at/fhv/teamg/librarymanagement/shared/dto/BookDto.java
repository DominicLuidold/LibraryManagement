package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookDto implements Serializable {
    private static final long serialVersionUID = 7234119134192614849L;

    /* Medium properties */
    private final UUID id;
    private final LocalDate releaseDate;
    private final String storageLocation;
    private final String title;
    private final UUID topic;
    private final List<String> tags;
    private final String availability;
    private final UUID mediumId;

    /* Book properties */
    private final String author;
    private final String isbn10;
    private final String isbn13;
    private final String languageKey;
    private final String publisher;

    private BookDto(BookDtoBuilder bookDtoBuilder) {
        this.id = bookDtoBuilder.id;
        this.releaseDate = bookDtoBuilder.releaseDate;
        this.storageLocation = bookDtoBuilder.storageLocation;
        this.title = bookDtoBuilder.title;
        this.topic = bookDtoBuilder.topic;
        this.tags = bookDtoBuilder.tags;
        this.availability = bookDtoBuilder.availability;

        this.author = bookDtoBuilder.author;
        this.isbn10 = bookDtoBuilder.isbn10;
        this.isbn13 = bookDtoBuilder.isbn13;
        this.languageKey = bookDtoBuilder.languageKey;
        this.publisher = bookDtoBuilder.publisher;
        this.mediumId = bookDtoBuilder.mediumId;
    }

    public static class BookDtoBuilder {
        private UUID id;
        private LocalDate releaseDate;
        private String storageLocation;
        private String title;
        private UUID topic;
        private List<String> tags;
        private String availability;

        private String author;
        private String isbn10;
        private String isbn13;
        private String languageKey;
        private String publisher;
        private UUID mediumId;

        public BookDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public BookDtoBuilder(UUID id) {
            this.id = id;
        }

        public BookDtoBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public BookDtoBuilder storageLocation(String storageLocation) {
            this.storageLocation = storageLocation;
            return this;
        }

        public BookDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookDtoBuilder topic(UUID topic) {
            this.topic = topic;
            return this;
        }

        public BookDtoBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public BookDtoBuilder availability(String availability) {
            this.availability = availability;
            return this;
        }

        public BookDtoBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookDtoBuilder isbn10(String isbn10) {
            this.isbn10 = isbn10;
            return this;
        }

        public BookDtoBuilder isbn13(String isbn13) {
            this.isbn13 = isbn13;
            return this;
        }

        public BookDtoBuilder languageKey(String languageKey) {
            this.languageKey = languageKey;
            return this;
        }

        public BookDtoBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookDtoBuilder mediumId(UUID mediumId) {
            this.mediumId = mediumId;
            return this;
        }

        public BookDto build() {
            return new BookDto(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public String getStorageLocation() {
        return this.storageLocation;
    }

    public String getTitle() {
        return this.title;
    }

    public UUID getTopic() {
        return this.topic;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getAvailability() {
        return this.availability;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIsbn10() {
        return this.isbn10;
    }

    public String getIsbn13() {
        return this.isbn13;
    }

    public String getLanguageKey() {
        return this.languageKey;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public UUID getMediumId() {
        return mediumId;
    }
}
