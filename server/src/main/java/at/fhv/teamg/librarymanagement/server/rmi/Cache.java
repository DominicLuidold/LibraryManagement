package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.BookService;
import at.fhv.teamg.librarymanagement.server.domain.DvdService;
import at.fhv.teamg.librarymanagement.server.domain.GameService;
import at.fhv.teamg.librarymanagement.server.domain.TopicService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.time.LocalDate;
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
    private List<DvdDto> dvdCache;
    private List<GameDto> gameCache;
    private List<TopicDto> topicCache;
    private List<UserDto> userCache;
    private final Timer timer = new Timer();

    private Cache() {
        LOG.info("cache preload");
        synchronized (lock) {
            bookCache = new BookService().getAllBooks();
        }

        synchronized (lock) {
            topicCache = new TopicService().getAllTopics();
        }

        synchronized (lock) {
            userCache = new UserService().getAllUsers();
        }

        synchronized (lock) {
            dvdCache = new DvdService().getAllDvds();
        }

        synchronized (lock) {
            gameCache = new GameService().getAllGames();
        }

        //startTimer();
        LOG.info("cache ready");
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

    /**
     * Search cached dvds.
     *
     * @param search search dto
     * @return list of filtered dvds
     */
    public List<DvdDto> searchDvd(DvdDto search) {
        String title = search.getTitle();
        String director = search.getDirector();
        LocalDate releaseDate = search.getReleaseDate();
        UUID topic = search.getTopic();

        return dvdCache.stream()
            .filter(dvdDto -> title.equals("") || dvdDto.getTitle().contains(title))
            .filter(dvdDto -> director.equals("") || dvdDto.getDirector().contains(director))
            .filter(dvdDto -> releaseDate.equals(LocalDate.MIN)
                || dvdDto.getReleaseDate().equals(releaseDate))
            .filter(dvdDto -> topic == null || dvdDto.getTopic().equals(topic))
            .collect(Collectors.toList());
    }

    /**
     * Search cached games.
     *
     * @param search search dto
     * @return list of filtered games
     */
    public List<GameDto> searchGame(GameDto search) {
        String title = search.getTitle();
        String developer = search.getDeveloper();
        String platforms = search.getPlatforms();
        UUID topic = search.getTopic();

        return gameCache.stream()
            .filter(dvdDto -> title.equals("") || dvdDto.getTitle().contains(title))
            .filter(dvdDto -> developer.equals("") || dvdDto.getDeveloper().contains(developer))
            .filter(dvdDto -> platforms.equals("") || dvdDto.getPlatforms().contains(platforms))
            .filter(dvdDto -> topic == null || dvdDto.getTopic().equals(topic))
            .collect(Collectors.toList());
    }

    /**
     * Invalidate book cache.
     */
    public void invalidateBookCache() {
        new Thread(() -> {
            LOG.info("updating books...");
            synchronized (lock) {
                bookCache = new BookService().getAllBooks();
            }
        }).start();
    }

    /**
     * Invalidate dvd cache.
     */
    public void invalidateDvdCache() {
        new Thread(() -> {
            LOG.info("updating dvds...");
            synchronized (lock) {
                dvdCache = new DvdService().getAllDvds();
            }
        }).start();
    }

    /**
     * Invalidate game cache.
     */
    public void invalidateGameCache() {
        new Thread(() -> {
            LOG.info("updating games...");
            synchronized (lock) {
                gameCache = new GameService().getAllGames();
            }
        }).start();
    }

    public List<TopicDto> getAllTopics() {
        return topicCache;
    }

    public List<UserDto> getAllUsers() {
        return userCache;
    }

    /**
     * Start timers to query db periodically for changes.
     * Only required when db is accessed by multiple servers.
     */
    private void startTimer() {
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
