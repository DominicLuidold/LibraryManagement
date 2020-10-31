package at.fhv.teamg.librarymanagement.shared.ifaces;

import java.rmi.Remote;

public interface Demo extends Remote {
    int justReturnTheNumberToTestRmi(int number);
}
