package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class ReservationDto implements Serializable {
    private static final long serialVersionUID = -8940871800405364694L;
    private final UUID id;
    private final LocalDate endDate;
    private final LocalDate startDate;
    private final UUID mediumId;
    private final UUID userId;

    private ReservationDto(ReservationDtoBuilder reservationDtoBuilder) {
        this.id = reservationDtoBuilder.id;
        this.endDate = reservationDtoBuilder.endDate;
        this.startDate = reservationDtoBuilder.startDate;
        this.mediumId = reservationDtoBuilder.mediumId;
        this.userId = reservationDtoBuilder.userId;
    }

    public static class ReservationDtoBuilder {
        private UUID id;
        private LocalDate endDate;
        private LocalDate startDate;
        private UUID mediumId;
        private UUID userId;


        public ReservationDtoBuilder(UUID id) {
            this.id = id;
        }

        public ReservationDtoBuilder() {
            //intentional empty
        }

        public ReservationDtoBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationDtoBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationDtoBuilder mediumId(UUID mediumId) {
            this.mediumId = mediumId;
            return this;
        }

        public ReservationDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }


        /**
         * Build a new ReservationDto.
         *
         * @return new ReservationDto
         */
        public ReservationDto build() {
            ReservationDto reservationDto = new ReservationDto(this);
            validateReservationDto(reservationDto);
            return reservationDto;
        }

        private void validateReservationDto(ReservationDto reservationDto) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
        }
    }

    public UUID getId() {
        return this.id;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public UUID getmediumId() {
        return this.mediumId;
    }

    public UUID getuserId() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "ReservationDto{"
            + "id=" + id
            + ", endDate=" + endDate
            + ", startDate=" + startDate
            + ", mediumId='" + mediumId + '\''
            + ", userId=" + userId
            + '}';
    }
}
