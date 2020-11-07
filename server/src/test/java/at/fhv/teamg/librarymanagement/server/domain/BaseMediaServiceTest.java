package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BaseMediaServiceTest {

    @Test
    public void getAvailability_shouldReturnCorrectAvailability() {
        // Mock MediumCopy entities
        MediumCopy mediumCopyMock1 = mock(MediumCopy.class);
        when(mediumCopyMock1.isAvailable()).thenReturn(true);

        MediumCopy mediumCopyMock2 = mock(MediumCopy.class);
        when(mediumCopyMock2.isAvailable()).thenReturn(false);

        Set<MediumCopy> mediumCopyMocks = new HashSet<>();
        mediumCopyMocks.add(mediumCopyMock1);
        mediumCopyMocks.add(mediumCopyMock2);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getCopies()).thenReturn(mediumCopyMocks);

        // Create instance of abstract class
        BaseMediaService baseMediaService = mock(
            BaseMediaService.class,
            Mockito.CALLS_REAL_METHODS
        );

        // Assertions
        String result = baseMediaService.getAvailability(mediumMock);

        assertEquals(result, "1/2", "Result should match expected pattern");
    }
}
