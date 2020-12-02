package at.fhv.teamg.librarymanagement.server.persistence.dao;

import at.fhv.teamg.librarymanagement.server.persistence.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Message;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MessageDao extends BaseDao<Message> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Message> find(UUID uuid) {
        return this.find(Message.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Message> persist(Message elem) {
        return this.persist(Message.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Message> update(Message elem) {
        return this.update(Message.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Message elem) {
        return this.remove(Message.class, elem);
    }

    public List<Message> getAll() {
        return super.getAll(Message.class);
    }
}
