package at.fhv.teamg.librarymanagement.server.ldap;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapConnector {
    private static final String serverUrlFH =
        "ldaps://dc01.ad.uclv.net:636/ou=fhusers,dc=ad,dc=uclv,dc=net";

    /**
     * Authenticate User against LDAP.
     * @throws Exception if login failed
     */
    public static boolean authenticateJndi(String username, String password) throws Exception {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(
            Context.PROVIDER_URL, serverUrlFH);
        props.put(Context.SECURITY_PRINCIPAL, "cn=***REMOVED***2,ou=SpecialUsers,dc=ad,dc=uclv,dc=net");
        props.put(Context.SECURITY_CREDENTIALS, "1d48oOffxMXb");


        InitialDirContext context = new InitialDirContext(props);

        SearchControls ctrls = new SearchControls();
        ctrls.setReturningAttributes(new String[] { "givenName", "sn","memberOf" });
        ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult>
            answers = context.search("ou=fhusers", "(uid=" + username + ")", ctrls);
        javax.naming.directory.SearchResult result = answers.nextElement();

        String user = result.getNameInNamespace();

        try {
            props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, serverUrlFH);
            props.put(Context.SECURITY_PRINCIPAL, user);
            props.put(Context.SECURITY_CREDENTIALS, password);

            context = new InitialDirContext(props);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
