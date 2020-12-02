package at.fhv.teamg.librarymanagement.server.persistance.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String isbn10;

    @Column(nullable = false, unique = true)
    private String isbn13;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String languageKey;

    @OneToOne(mappedBy = "book")
    private Medium medium;

    public UUID getId() {
        return id;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLanguageKey() {
        return languageKey;
    }

    public Medium getMedium() {
        return medium;
    }
}
