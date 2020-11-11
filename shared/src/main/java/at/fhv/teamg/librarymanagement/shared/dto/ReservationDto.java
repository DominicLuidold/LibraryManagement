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

        public ReservationDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public ReservationDtoBuilder(UUID id) {
            this.id = id;
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

        public ReservationDto build() {
            return new ReservationDto(this);
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

    public UUID getMediumId() {
        return this.mediumId;
    }

    public UUID getUserId() {
        return this.userId;
    }
}
