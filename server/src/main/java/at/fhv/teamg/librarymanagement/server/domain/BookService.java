package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.TopicDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
                .title(book.getMedium().getTitle())
                .storageLocation(book.getMedium().getStorageLocation())
                .author(book.getAuthor())
                .availability(getAvailability(book.getMedium()))
                .topic(book.getMedium().getTopic().getId());

            dtoList.add(builder.build());
        });

        return dtoList;
    }

    /**
     * Get all books from cache.
     *
     * @return cached books
     */
    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new LinkedList<>();

        getAll().forEach(book -> {
            BookDto.BookDtoBuilder builder = new BookDto.BookDtoBuilder(book.getId())
                .title(book.getMedium().getTitle())
                .storageLocation(book.getMedium().getStorageLocation())
                .author(book.getAuthor())
                .availability(getAvailability(book.getMedium()))
                .topic(book.getMedium().getTopic().getId());

            bookDtos.add(builder.build());
        });

        return bookDtos;
    }

    protected List<Book> getAll() {
        BookDao dao = new BookDao();
        return dao.getAll();
    }

    protected List<Book> findBy(String title, String author, String isbn13, String topic) {
        return new BookDao().findBy(title, author, isbn13, topic);
    }
}
