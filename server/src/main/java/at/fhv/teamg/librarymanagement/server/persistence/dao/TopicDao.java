package at.fhv.teamg.librarymanagement.server.persistence.dao;

import at.fhv.teamg.librarymanagement.server.persistence.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Topic;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TopicDao extends BaseDao<Topic> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Topic> find(UUID uuid) {
        return find(Topic.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Topic> persist(Topic elem) {
        return persist(Topic.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Topic> update(Topic elem) {
        return update(Topic.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Topic elem) {
        return remove(Topic.class, elem);
    }

    public List<Topic> getAll() {
        return super.getAll(Topic.class);
    }
}
