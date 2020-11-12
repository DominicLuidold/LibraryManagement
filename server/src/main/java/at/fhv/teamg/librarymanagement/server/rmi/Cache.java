package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.BookService;
import at.fhv.teamg.librarymanagement.server.domain.TopicService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cache {
    private static final Logger LOG = LogManager.getLogger(Cache.class);
    private static Cache instance;
    private final Object lock = new Object();
    private final int minute = 1000 * 60;
    private List<BookDto> bookCache;
    private List<TopicDto> topicCache;
    private List<UserDto> userCache;

    private Cache() {
        LOG.info("cache instance");
        synchronized (lock) {
            bookCache = new BookService().getAllBooks();
        }

        synchronized (lock) {
            topicCache = new TopicService().getAllTopics();
        }

        synchronized (lock) {
            userCache = new UserService().getAllUsers();
        }

        startTimer();
    }

    /**
     * Get Cache instance.
     *
     * @return instance of cache
     */
    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    /**
     * Search cached books.
     *
     * @param search search dto
     * @return list of filtered books
     */
    public List<BookDto> searchBook(BookDto search) {
        String title = search.getTitle();
        String author = search.getAuthor();
        String isbn13 = search.getIsbn13();
        UUID topic = search.getTopic();


        return bookCache.stream()
            .filter(bookDto -> title.equals("") || bookDto.getTitle().contains(title))
            .filter(bookDto -> author.equals("") || bookDto.getAuthor().contains(author))
            .filter(bookDto -> isbn13.equals("") || bookDto.getIsbn13().contains(isbn13))
            .filter(bookDto -> topic == null || bookDto.getTopic().equals(topic))
            .collect(Collectors.toList());
    }

    public List<TopicDto> getAllTopics() {
        return topicCache;
    }

    public List<UserDto> getAllUsers() {
        return userCache;
    }

    private void startTimer() {
        Timer timer = new Timer();
        TimerTask updateBooks = new TimerTask() {
            @Override
            public void run() {
                LOG.info("updating books...");
                synchronized (lock) {
                    bookCache = new BookService().getAllBooks();
                }
            }
        };
        timer.scheduleAtFixedRate(updateBooks, minute * 5, minute * 5);

        TimerTask updateTopics = new TimerTask() {
            @Override
            public void run() {
                LOG.info("updating topics...");
                synchronized (lock) {
                    topicCache = new TopicService().getAllTopics();
                }
            }
        };
        timer.scheduleAtFixedRate(updateTopics, minute * 5, minute * 5);

        TimerTask updateUsers = new TimerTask() {
            @Override
            public void run() {
                LOG.info("updating users...");
                synchronized (lock) {
                    userCache = new UserService().getAllUsers();
                }
            }
        };
        timer.scheduleAtFixedRate(updateUsers, minute * 5, minute * 5);

    }
}
