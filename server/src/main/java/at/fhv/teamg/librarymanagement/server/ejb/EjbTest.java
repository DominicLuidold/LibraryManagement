package at.fhv.teamg.librarymanagement.server.ejb;

import at.fhv.teamg.librarymanagement.shared.ifaces.ejb.EjbTestRemote;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote(EjbTestRemote.class)
@Stateless
public class EjbTest implements EjbTestRemote {
    @Override
    public String getName(String s) {
        return "name is: " + s;
    }
}
