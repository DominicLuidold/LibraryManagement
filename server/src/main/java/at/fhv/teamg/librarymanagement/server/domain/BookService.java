package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.domain.common.Searchable;
import at.fhv.teamg.librarymanagement.server.domain.common.Utils;
import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookService extends BaseMediaService implements Searchable<BookDto> {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDto> search(BookDto bookDto) {
        String topic = "";
        if (bookDto.getTopic() != null) {
            Optional<Topic> topicEntity = findTopicById(bookDto.getTopic());

            if (topicEntity.isPresent()) {
                topic = topicEntity.get().getName();
            }
        }

        List<Book> entities = findBy(
            bookDto.getTitle(),
            bookDto.getAuthor(),
            bookDto.getIsbn13(),
            topic
        );

        List<BookDto> dtoList = new LinkedList<>();
        entities.forEach(book -> {
            BookDto.BookDtoBuilder builder = new BookDto.BookDtoBuilder(book.getId())
                .author(book.getAuthor())
                .availability(getAvailability(book.getMedium()))
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn10())
                .languageKey(book.getLanguageKey())
                .publisher(book.getPublisher())
                .releaseDate(book.getMedium().getReleaseDate())
                .storageLocation(book.getMedium().getStorageLocation())
                .title(book.getMedium().getTitle())
                .topic(book.getMedium().getTopic().getId())
                .mediumId(book.getMedium().getId());

            dtoList.add(builder.build());
        });

        return dtoList;
    }

    /**
     * Get all books.
     *
     * @return all books
     */
    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new LinkedList<>();

        getAll().forEach(book -> {
            bookDtos.add(Utils.createBookDto(book, getAvailability(book.getMedium())));
        });

        return bookDtos;
    }

    /**
     * Get Book by Medium Id.
     *
     * @param mediumId uuid
     * @return BookDto
     */
    public Optional<BookDto> getBookByMediumId(UUID mediumId) {
        Optional<Medium> medium = findMediumById(mediumId);
        if (medium.isPresent()) {
            Book book = medium.get().getBook();
            if (book != null) {
                return Optional.of(Utils.createBookDto(book, getAvailability(book.getMedium())));
            }
        }
        return Optional.empty();
    }

    /**
     * Get Book by MediumCopyId.
     *
     * @param mediumCopyId uuid
     * @return BookDto
     */
    public Optional<BookDto> getBookByMediumCopyId(UUID mediumCopyId) {
        Optional<MediumCopy> mediumCopy = findMediumCopyById(mediumCopyId);
        if (mediumCopy.isPresent()) {
            Book book = mediumCopy.get().getMedium().getBook();
            if (book != null) {
                return Optional.of(Utils.createBookDto(book, getAvailability(book.getMedium())));
            }
        }
        return Optional.empty();
    }

    protected List<Book> getAll() {
        BookDao dao = new BookDao();
        return dao.getAll();
    }

    protected List<Book> findBy(String title, String author, String isbn13, String topic) {
        return new BookDao().findBy(title, author, isbn13, topic);
    }
}
