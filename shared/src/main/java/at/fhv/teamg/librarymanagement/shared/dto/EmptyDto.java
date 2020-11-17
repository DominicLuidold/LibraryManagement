package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;

public class EmptyDto implements Dto, Serializable {
    private static final long serialVersionUID = 8065141009545487393L;

    private EmptyDto() {
        // Intentionally empty
    }
}
