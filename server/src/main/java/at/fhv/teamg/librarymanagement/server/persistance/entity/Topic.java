package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Topic extends BaseTagTopic {
    @OneToMany(mappedBy = "topic")
    private Set<Medium> media;

    public Set<Medium> getMedia() {
        return media;
    }
}
