package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;
import java.util.UUID;

public class LoginDto implements Dto, Serializable {
    private static final long serialVersionUID = 1326320486087070517L;

    private final UUID id;
    private final String username;
    private final String password;
    private final boolean externalLibrary;
    private final UserRoleName userRoleName;
    private final boolean isValid;

    private LoginDto(LoginDtoBuilder builder) {
        id = builder.id;
        username = builder.username;
        password = builder.password;
        externalLibrary = builder.externalLibrary;
        userRoleName = builder.userRoleName;
        isValid = builder.isValid;
    }

    public static class LoginDtoBuilder {
        private UUID id;
        private String username;
        private String password;
        private boolean externalLibrary;
        private UserRoleName userRoleName;
        private boolean isValid;

        public LoginDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public LoginDtoBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public LoginDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public LoginDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginDtoBuilder withIsExternalLibrary(boolean externalLibrary) {
            this.externalLibrary = externalLibrary;
            return this;
        }

        public LoginDtoBuilder withUserRoleName(UserRoleName userRoleName) {
            this.userRoleName = userRoleName;
            return this;
        }

        public LoginDtoBuilder withIsValid(Boolean isValid) {
            this.isValid = isValid;
            return this;
        }

        public LoginDto build() {
            return new LoginDto(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isExternalLibrary() {
        return externalLibrary;
    }

    public UserRoleName getUserRoleName() {
        return userRoleName;
    }

    public boolean getIsValid() {
        return isValid;
    }

    @Override
    public String toString() {
        return "LoginDto{"
            + "id=" + id
            + ", username='" + username + '\''
            + ", externalLibrary=" + externalLibrary
            + ", userRoleName=" + userRoleName
            + ", isValid=" + isValid
            + '}';
    }
}