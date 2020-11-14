package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.ldap.LdapConnector;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationService {
    private static final Logger LOG = LogManager.getLogger(AuthenticationService.class);

    /**
     * Authenticate User against LDAP.
     * @param loginUser user to login.
     * @return LoginDto with isValid set to true if login was Successfully.
     */
    public LoginDto authenticateUser(LoginDto loginUser) {
        boolean isValid = false;
        try {
            LOG.info("Try to Login user with Username: {}", loginUser.getUsername());
            isValid = LdapConnector.authenticateJndi(
                loginUser.getUsername(),
                loginUser.getPassword()
            );
        } catch (Exception exception) {
            LOG.info("Failed login of User: {}", loginUser.getUsername());
        }

        LoginDto loggedInUser = new LoginDto.LoginDtoBuilder()
            .username(loginUser.getUsername())
            .isValid(isValid)
            .build();

        return loggedInUser;
    }
}
