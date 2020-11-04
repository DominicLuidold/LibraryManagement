package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class BookDto implements Serializable {
    private static final long serialVersionUID = 7234119134192614849L;
    private final UUID id;
    private final LocalDate releaseDate;
    private final String storageLocation;
    private final String title;
    private final String topic;

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

        this.author = bookDtoBuilder.author;
        this.isbn10 = bookDtoBuilder.isbn10;
        this.isbn13 = bookDtoBuilder.isbn13;
        this.languageKey = bookDtoBuilder.languageKey;
        this.publisher = bookDtoBuilder.publisher;
    }


    public static class BookDtoBuilder {
        private UUID id;
        private LocalDate releaseDate;
        private String storageLocation;
        private String title;
        private String topic;

        private String author;
        private String isbn10;
        private String isbn13;
        private String languageKey;
        private String publisher;

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

        public BookDtoBuilder topic(String topic) {
            this.topic = topic;
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

        /**
         * Build a new BookDto.
         * @return new BookDto
         */
        public BookDto build() {
            BookDto bookDto =  new BookDto(this);
            validatebookDto(bookDto);
            return bookDto;
        }

        private void validatebookDto(BookDto bookDto) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
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

    public String getTopic() {
        return this.topic;
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

    @Override
    public String toString() {
        return "BookDto{"
                + "id=" + id
                + ", releaseDate=" + releaseDate
                + ", storageLocation='" + storageLocation + '\''
                + ", title='" + title + '\''
                + ", topic='" + topic + '\''
                + ", author='" + author + '\''
                + ", isbn10='" + isbn10 + '\''
                + ", isbn13='" + isbn13 + '\''
                + ", languageKey='" + languageKey + '\''
                + ", publisher='" + publisher + '\''
                + '}';
    }
}
