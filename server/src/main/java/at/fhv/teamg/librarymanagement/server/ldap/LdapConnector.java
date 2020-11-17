package at.fhv.teamg.librarymanagement.server.ldap;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

public class LdapConnector {
    private static final String serverUrlFH =
        "ldaps://dc01.ad.uclv.net:636/ou=fhusers,dc=ad,dc=uclv,dc=net";

    /**
     * Authenticate User against LDAP.
     */
    public static boolean authenticateJndi(String username, String password) {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, serverUrlFH);
        props.put(Context.SECURITY_PRINCIPAL, username);
        props.put(Context.SECURITY_CREDENTIALS, password);

        try {
            InitialDirContext context = new InitialDirContext(props);
            return true;
        } catch (NamingException e) {
            return false;
        }
    }
}
