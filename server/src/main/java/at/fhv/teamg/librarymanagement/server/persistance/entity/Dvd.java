package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Dvd {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String actors;

    @Column(nullable = false)
    private String studio;

    @Column(nullable = false)
    private String ageRestriction;

    @OneToOne(mappedBy = "dvd")
    private Medium medium;

    public UUID getId() {
        return id;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getStudio() {
        return studio;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public Medium getMedium() {
        return medium;
    }
}
