package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class MediumCopy {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @ManyToOne
    private Medium medium;

    @OneToMany(mappedBy = "mediumCopy")
    private Set<Lending> lending = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Medium getMedium() {
        return medium;
    }

    public Set<Lending> getLending() {
        return lending;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
