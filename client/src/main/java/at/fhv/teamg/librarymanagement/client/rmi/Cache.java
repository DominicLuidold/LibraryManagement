package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.client.controller.LendingController;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cache {
    private static Cache instance;
    private static final Logger LOG = LogManager.getLogger(Cache.class);
    private static final long MIN = 1000 * 60;
    private final Object lock = new Object();
    Timer timer = new Timer();
    private List<UserDto> userCache;
    private List<TopicDto> topicCache;

    private Cache() {
        startTimers();
    }

    /**
     * Get instance of cache singleton.
     *
     * @return instance of Cache
     */
    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    /**
     * Get all users from cache.
     *
     * @return List of UserDto
     */
    public List<UserDto> getAllUsers() {
        if (userCache == null) {
            synchronized (lock) {
                try {
                    userCache = RmiClient.getInstance().getAllUsers();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return userCache;
    }

    /**
     * Get all Topics from cache.
     *
     * @return List of TopicDto
     */
    public List<TopicDto> getAllTopics() {
        if (topicCache == null) {
            synchronized (lock) {
                try {
                    topicCache = RmiClient.getInstance().getAllTopics();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return topicCache;
    }


    private void startTimers() {
        LOG.info("preload");
        TimerTask updateTopics = new TimerTask() {
            @Override
            public void run() {
                LOG.info("updating topics...");
                synchronized (lock) {
                    try {
                        topicCache = RmiClient.getInstance().getAllTopics();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.schedule(updateTopics, 0);

        TimerTask updateUsers = new TimerTask() {
            @Override
            public void run() {
                LOG.info("updating users...");
                synchronized (lock) {
                    try {
                        userCache = RmiClient.getInstance().getAllUsers();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(updateUsers, 0, MIN * 5);
    }
}
