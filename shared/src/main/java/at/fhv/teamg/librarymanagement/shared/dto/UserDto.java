package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 4487858801782386377L;

    private final UUID id;
    private final String username;
    private final String name;
    private final String address;
    private final String email;
    private final String phone;
    private final boolean externalLibrary;
    private final UUID roleId;
    private final Set<UUID> reservations;
    private final Set<UUID> lendings;

    /**
     * Dto for User.
     *
     * @param builder builds the DTO
     */
    public UserDto(UserDtoBuilder builder) {
        id = builder.id;
        username = builder.username;
        name = builder.name;
        address = builder.address;
        email = builder.email;
        phone = builder.phone;
        externalLibrary = builder.externalLibrary;
        roleId = builder.roleId;
        reservations = builder.reservations;
        lendings = builder.lendings;
    }

    public static class UserDtoBuilder {
        private UUID id;
        private String username;
        private String name;
        private String address;
        private String email;
        private String phone;
        private boolean externalLibrary;
        private UUID roleId;
        private Set<UUID> reservations;
        private Set<UUID> lendings;

        public UserDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public UserDtoBuilder(UUID id) {
            this.id = id;
        }

        public UserDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDtoBuilder externalLibrary(boolean externalLibrary) {
            this.externalLibrary = externalLibrary;
            return this;
        }

        public UserDtoBuilder roleId(UUID roleId) {
            this.roleId = roleId;
            return this;
        }

        public UserDtoBuilder reservations(Set<UUID> reservations) {
            this.reservations = reservations;
            return this;
        }

        public UserDtoBuilder lendings(Set<UUID> lendings) {
            this.lendings = lendings;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

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

    public UUID getRoleId() {
        return roleId;
    }

    public Set<UUID> getReservations() {
        return reservations;
    }

    public Set<UUID> getLendings() {
        return lendings;
    }
}
