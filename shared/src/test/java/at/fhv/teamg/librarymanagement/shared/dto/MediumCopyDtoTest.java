package at.fhv.teamg.librarymanagement.shared.dto;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MediumCopyDtoTest {
    @Test
    void mediumCopyDtoBuilder_shouldReturnValidDto() {
        UUID id = UUID.fromString("2401e527-4bd3-4b67-8641-21119a686ed1");
        boolean isAvailable = true;
        UUID mediumId = UUID.fromString("2401e527-4711-4711-4711-21119a686ed1");

        MediumCopyDto mediumCopyDto = new MediumCopyDto.CopyDtoBuilder(id)
            .isAvailable(isAvailable)
            .mediumID(mediumId)
            .build();

        assertSame(mediumCopyDto.getId(), id, "UUID should be the same");
        assertSame(mediumCopyDto.isAvailable(), isAvailable, "isAvailable should be the same");
        assertSame(mediumCopyDto.getMediumID(), mediumId, "mediumId should be the same");
    }
}