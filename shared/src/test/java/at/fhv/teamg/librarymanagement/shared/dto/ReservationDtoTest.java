package at.fhv.teamg.librarymanagement.shared.dto;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ReservationDtoTest {

    @Test
    void reservationDtoBuilder_shouldReturnValidDto() {
        UUID id = UUID.fromString("2401e527-4bd3-4b67-8641-21119a686ed1");
        LocalDate startDate = LocalDate.parse("2009-02-20");
        LocalDate endDate = LocalDate.parse("2009-02-20");
        UUID mediumId = UUID.fromString("2401e527-4711-4711-4711-21119a686ed1");
        UUID userId = UUID.fromString("2401e527-1337-1337-1337-21119a686ed1");

        ReservationDto reservationDto = new ReservationDto.ReservationDtoBuilder(id)
            .startDate(startDate)
            .endDate(endDate)
            .mediumId(mediumId)
            .userId(userId)
            .build();

        assertSame(reservationDto.getId(), id, "UUID should be the same");
        assertSame(reservationDto.getStartDate(), startDate, "startDate should be the same");
        assertSame(reservationDto.getEndDate(), endDate, "endDate should be the same");
        assertSame(reservationDto.getMediumId(), mediumId, "mediumId should be the same");
        assertSame(reservationDto.getUserId(), userId, "userId should be the same");

    }
}
