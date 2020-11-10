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
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String name;

    @Column
    private String address;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

    @Column(nullable = false)
    private boolean externalLibrary = false;

    @ManyToOne
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Lending> lendings = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isExternalLibrary() {
        return externalLibrary;
    }

    public UserRole getRole() {
        return role;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Set<Lending> getLendings() {
        return lendings;
    }
}
