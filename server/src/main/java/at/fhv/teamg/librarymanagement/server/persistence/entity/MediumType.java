package at.fhv.teamg.librarymanagement.server.persistence.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class MediumType {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int maxLendingDays;

    @Column(nullable = false)
    private int maxExtensionDays;

    @Column(nullable = false)
    private int maxExtensionCount;

    @OneToMany(mappedBy = "type")
    private List<Medium> medium = new LinkedList<>();

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxLendingDays() {
        return maxLendingDays;
    }

    public int getMaxExtensionDays() {
        return maxExtensionDays;
    }

    public int getMaxExtensionCount() {
        return maxExtensionCount;
    }

    public List<Medium> getMedium() {
        return medium;
    }
}
