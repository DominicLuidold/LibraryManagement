package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Game {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String developer;

    @Column(nullable = false)
    private String ageRestriction;

    @Column(nullable = false)
    private String platforms;

    @OneToOne(mappedBy = "game")
    private Medium medium;

    public UUID getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getPlatforms() {
        return platforms;
    }

    public Medium getMedium() {
        return medium;
    }
}
