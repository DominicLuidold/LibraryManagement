package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import java.util.LinkedList;
import java.util.List;

public class BookService extends BaseMediaService implements Searchable<BookDto> {
    BookDao bookDao;

    public BookService() {
        bookDao = new BookDao();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDto> search(BookDto bookDto) {
        List<Book> entities = bookDao.findBy(
            bookDto.getTitle(),
            bookDto.getAuthor(),
            bookDto.getIsbn13(),
            bookDto.getTopic()
        );

        List<BookDto> dtoList = new LinkedList<>();
        entities.forEach(book -> {
            BookDto.BookDtoBuilder builder = new BookDto.BookDtoBuilder(book.getId())
                .title(book.getMedium().getTitle())
                .storageLocation(book.getMedium().getStorageLocation())
                .author(book.getAuthor())
                .availability(getAvailability(book.getMedium()));

            dtoList.add(builder.build());
        });

        return dtoList;
    }
}