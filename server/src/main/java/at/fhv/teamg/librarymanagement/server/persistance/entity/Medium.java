package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Medium {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String storageLocation;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @ManyToOne
    private MediumType type;

    @OneToMany(mappedBy = "medium")
    private Set<MediumCopy> copies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "medium")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToOne
    private Book book;

    @OneToOne
    private Dvd dvd;

    @OneToOne
    private Game game;

    public UUID getId() {
        return id;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public MediumType getType() {
        return type;
    }

    public Set<MediumCopy> getCopies() {
        return copies;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Book getBook() {
        return book;
    }

    public Dvd getDvd() {
        return dvd;
    }

    public Game getGame() {
        return game;
    }
}
