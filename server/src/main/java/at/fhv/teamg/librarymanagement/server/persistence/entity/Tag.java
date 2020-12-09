package at.fhv.teamg.librarymanagement.server.persistence.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Tag extends BaseTagTopic {
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private Set<Medium> media = new LinkedHashSet<>();

    public Set<Medium> getMedia() {
        return media;
    }
}
