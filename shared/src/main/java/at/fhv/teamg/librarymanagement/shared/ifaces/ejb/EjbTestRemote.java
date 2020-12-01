package at.fhv.teamg.librarymanagement.shared.ifaces.ejb;

import java.io.Serializable;
import javax.ejb.Remote;

@Remote
public interface EjbTestRemote extends Serializable {
    String getName(String var1);
}
