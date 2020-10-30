package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tag {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private Set<Medium> media = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Medium> getMedia() {
        return media;
    }
}
