package at.fhv.teamg.librarymanagement.server.common;

import at.fhv.teamg.librarymanagement.server.domain.BookService;
import at.fhv.teamg.librarymanagement.server.domain.DvdService;
import at.fhv.teamg.librarymanagement.server.domain.GameService;
import at.fhv.teamg.librarymanagement.server.domain.TopicService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
    private final HashMap<UUID, BookDto> bookCache = new HashMap<>();
    private final HashMap<UUID, DvdDto> dvdCache = new HashMap<>();
    private final HashMap<UUID, GameDto> gameCache = new HashMap<>();
    private final Timer timer = new Timer();

    private List<TopicDto> topicCache;
    private List<UserDto> userCache;
    private UUID customerId;
    private UUID externalLibraryId;

    private Cache() {
        LOG.info("Starting cache preload");

        LOG.debug("Preloading books");
        synchronized (lock) {
            new BookService().getAllBooks().forEach(bookDto -> bookCache.put(
                bookDto.getId(), bookDto
            ));
        }

        LOG.debug("Preloading dvds");
        synchronized (lock) {
            new DvdService().getAllDvds().forEach(dvdDto -> dvdCache.put(dvdDto.getId(), dvdDto));
        }

        LOG.debug("Preloading games");
        synchronized (lock) {
            new GameService().getAllGames()
                .forEach(gameDto -> gameCache.put(gameDto.getId(), gameDto));
        }

        LOG.debug("Preloading topics");
        synchronized (lock) {
            topicCache = new TopicService().getAllTopics();
        }

        LOG.debug("Preloading users");
        synchronized (lock) {
            var service = new UserService();
            userCache = service.getAllUsers();
            customerId = service.findUserRoleIdByName(UserRoleName.Customer);
            externalLibraryId = service.findUserRoleIdByName(UserRoleName.CustomerExternalLibrary);
        }

        LOG.info("Preloading done. Cache ready");
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
        String title = search.getTitle().toLowerCase();
        String author = search.getAuthor().toLowerCase();
        String isbn13 = search.getIsbn13();
        UUID topic = search.getTopic();

        return bookCache.values().stream()
            .filter(bookDto -> title.equals("") || bookDto.getTitle().toLowerCase().contains(title))
            .filter(
                bookDto -> author.equals("") || bookDto.getAuthor().toLowerCase().contains(author))
            .filter(
                bookDto -> isbn13.equals("") || bookDto.getIsbn13().contains(isbn13))
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
        String title = search.getTitle().toLowerCase();
        String director = search.getDirector().toLowerCase();
        LocalDate releaseDate = search.getReleaseDate();
        UUID topic = search.getTopic();

        return dvdCache.values().stream()
            .filter(dvdDto -> title.equals("") || dvdDto.getTitle().toLowerCase().contains(title))
            .filter(dvdDto -> director.equals("")
                || dvdDto.getDirector().toLowerCase().contains(director))
            .filter(dvdDto -> releaseDate.equals(LocalDate.MIN)
                || dvdDto.getReleaseDate().plusDays(1).isAfter(releaseDate))
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
        String title = search.getTitle().toLowerCase();
        String developer = search.getDeveloper().toLowerCase();
        String platforms = search.getPlatforms().toLowerCase();
        UUID topic = search.getTopic();

        return gameCache.values().stream()
            .filter(dvdDto -> title.equals("") || dvdDto.getTitle().toLowerCase().contains(title))
            .filter(dvdDto -> developer.equals("")
                || dvdDto.getDeveloper().toLowerCase().contains(developer))
            .filter(dvdDto -> platforms.equals("")
                || dvdDto.getPlatforms().toLowerCase().contains(platforms))
            .filter(dvdDto -> topic == null || dvdDto.getTopic().equals(topic))
            .collect(Collectors.toList());
    }

    public BookDto getBookDetail(UUID uuid) {
        return bookCache.getOrDefault(uuid, null);
    }

    public GameDto getGameDetail(UUID uuid) {
        return gameCache.getOrDefault(uuid, null);
    }

    public DvdDto getDvdDetail(UUID uuid) {
        return dvdCache.getOrDefault(uuid, null);
    }

    /**
     * Invalidate all books in cache.
     */
    public void invalidateBookCache() {
        new Thread(() -> {
            LOG.debug("Invalidating book cache");
            synchronized (lock) {
                new BookService().getAllBooks()
                    .forEach(bookDto -> bookCache.put(bookDto.getId(), bookDto));
            }
        }).start();
    }

    /**
     * Invalidate singe book in cache by Medium id.
     *
     * @param mediumId uuid
     */
    public void invalidateBookCacheMedium(UUID mediumId) {
        new Thread(() -> {
            LOG.debug("Invalidating book cache for medium [{}]", mediumId);
            synchronized (lock) {
                Optional<BookDto> bookDto = new BookService().getBookByMediumId(mediumId);
                if (bookDto.isPresent()) {
                    bookCache.replace(bookDto.get().getId(), bookDto.get());
                } else {
                    LOG.error("Book with id [{}] not found", mediumId);
                }
            }
        }).start();
    }

    /**
     * Invalidate singe book in cache by MediumCopy id.
     *
     * @param mediumCopyId uuid
     */
    public void invalidateBookCacheMediumCopy(UUID mediumCopyId) {
        new Thread(() -> {
            LOG.debug("Invalidating book cache for medium copy [{}]", mediumCopyId);
            synchronized (lock) {
                Optional<BookDto> bookDto = new BookService().getBookByMediumCopyId(mediumCopyId);
                if (bookDto.isPresent()) {
                    bookCache.replace(bookDto.get().getId(), bookDto.get());
                } else {
                    LOG.error("Book with id [{}] not found", mediumCopyId);
                }
            }
        }).start();
    }

    /**
     * Invalidate all dvds in cache.
     */
    public void invalidateDvdCache() {
        new Thread(() -> {
            LOG.debug("Invalidating dvd cache");
            synchronized (lock) {
                new DvdService().getAllDvds()
                    .forEach(dvdDto -> dvdCache.put(dvdDto.getId(), dvdDto));
            }
        }).start();
    }

    /**
     * Invalidate singe dvd in cache by Medium id.
     *
     * @param mediumId uuid
     */
    public void invalidateDvdCacheMedium(UUID mediumId) {
        new Thread(() -> {
            LOG.debug("Invalidating dvd cache for medium [{}]", mediumId);
            synchronized (lock) {
                Optional<DvdDto> dvdDto = new DvdService().getDvdByMediumId(mediumId);
                if (dvdDto.isPresent()) {
                    dvdCache.replace(dvdDto.get().getId(), dvdDto.get());
                } else {
                    LOG.error("Dvd with id [{}] not found", mediumId);
                }
            }
        }).start();
    }

    /**
     * Invalidate singe dvd in cache by MediumCopy id.
     *
     * @param mediumCopyId uuid
     */
    public void invalidateDvdCacheMediumCopy(UUID mediumCopyId) {
        new Thread(() -> {
            LOG.debug("Invalidating dvd cache for medium copy [{}]", mediumCopyId);
            synchronized (lock) {
                Optional<DvdDto> dvdDto = new DvdService().getDvdByMediumCopyId(mediumCopyId);
                if (dvdDto.isPresent()) {
                    dvdCache.replace(dvdDto.get().getId(), dvdDto.get());
                } else {
                    LOG.error("Dvd with id [{}] not found", mediumCopyId);
                }
            }
        }).start();
    }

    /**
     * Invalidate all games in cache.
     */
    public void invalidateGameCache() {
        new Thread(() -> {
            LOG.debug("Invalidating game cache");
            synchronized (lock) {
                new GameService().getAllGames()
                    .forEach(gameDto -> gameCache.put(gameDto.getId(), gameDto));
            }
        }).start();
    }

    /**
     * Invalidate singe game in cache by Medium id.
     *
     * @param mediumId uuid
     */
    public void invalidateGameCacheMedium(UUID mediumId) {
        new Thread(() -> {
            LOG.debug("Invalidating game cache for medium [{}]", mediumId);
            synchronized (lock) {
                Optional<GameDto> gameDto = new GameService().getGameByMediumId(mediumId);
                if (gameDto.isPresent()) {
                    gameCache.replace(gameDto.get().getId(), gameDto.get());
                } else {
                    LOG.error("Game with id [{}] not found", mediumId);
                }
            }
        }).start();
    }

    /**
     * Invalidate singe game in cache by MediumCopy id.
     *
     * @param mediumCopyId uuid
     */
    public void invalidateGameCacheMediumCopy(UUID mediumCopyId) {
        new Thread(() -> {
            LOG.debug("Invalidating game cache for medium copy [{}]", mediumCopyId);
            synchronized (lock) {
                Optional<GameDto> gameDto = new GameService().getGameByMediumCopyId(mediumCopyId);
                if (gameDto.isPresent()) {
                    gameCache.replace(gameDto.get().getId(), gameDto.get());
                } else {
                    LOG.error("Game with id [{}] not found", mediumCopyId);
                }
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
     * Returns all Users with UserRole Customer.
     *
     * @return list of customers
     */
    public List<UserDto> getAllCustomers() {
        return userCache.stream()
            .filter(
                userDto ->
                userDto.getRoleId().equals(customerId)
                || userDto.getRoleId().equals(externalLibraryId)
            )
            .collect(Collectors.toList());
    }

    /**
     * Start timers to query db periodically for changes.
     * Only required when db is accessed by multiple servers.
     */
    private void startTimer() {
        TimerTask updateBooks = new TimerTask() {
            @Override
            public void run() {
                LOG.debug("Invalidating book cache based on automated timer");
                synchronized (lock) {
                    new BookService().getAllBooks()
                        .forEach(bookDto -> bookCache.put(bookDto.getId(), bookDto));
                }
            }
        };
        timer.scheduleAtFixedRate(updateBooks, minute * 5, minute * 5);

        TimerTask updateTopics = new TimerTask() {
            @Override
            public void run() {
                LOG.debug("Invalidating topic cache based on automated timer");
                synchronized (lock) {
                    topicCache = new TopicService().getAllTopics();
                }
            }
        };
        timer.scheduleAtFixedRate(updateTopics, minute * 5, minute * 5);

        TimerTask updateUsers = new TimerTask() {
            @Override
            public void run() {
                LOG.debug("Invalidating user cache based on automated timer");
                synchronized (lock) {
                    userCache = new UserService().getAllUsers();
                }
            }
        };
        timer.scheduleAtFixedRate(updateUsers, minute * 5, minute * 5);

    }
}
