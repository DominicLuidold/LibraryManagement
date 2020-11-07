package at.fhv.teamg.librarymanagement.shared.dto;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class LendingDtoTest {

    @Test
    void lendingDtoBuilder_shouldReturnValidDto() {
        UUID id = UUID.fromString("2401e527-4bd3-4b67-8641-21119a686ed1");
        LocalDate startDate = LocalDate.parse("2009-02-20");
        LocalDate endDate = LocalDate.parse("2009-02-20");
        Integer renewalCount = 1337;
        UUID mediumCopyId = UUID.fromString("2401e527-4711-4711-4711-21119a686ed1");
        LocalDate returnDate = LocalDate.parse("2009-02-20");

        LendingDto lendingDto = new LendingDto.LendingDtoBuilder(id)
                .startDate(startDate)
                .endDate(endDate)
                .renewalCount(renewalCount)
                .mediumCopyId(mediumCopyId)
                .returnDate(returnDate)
                .build();

        assertSame(lendingDto.getId(), id, "UUID should be the same");
        assertSame(lendingDto.getStartDate(), startDate, "startDate should be the same");
        assertSame(lendingDto.getEndDate(), endDate, "endDate should be the same");
        assertSame(lendingDto.getRenewalCount(), renewalCount, "renewalCount should be the same");
        assertSame(lendingDto.getMediumCopyId(), mediumCopyId, "mediumCopyId should be the same");
        assertSame(lendingDto.getReturnDate(), returnDate, "returnDate should be the same");
    }
}
