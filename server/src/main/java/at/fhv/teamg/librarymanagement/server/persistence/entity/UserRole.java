package at.fhv.teamg.librarymanagement.server.persistence.entity;

import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleName name;

    @OneToMany(mappedBy = "role")
    private List<User> users = new LinkedList<>();

    public UUID getId() {
        return id;
    }

    public UserRoleName getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }
}
