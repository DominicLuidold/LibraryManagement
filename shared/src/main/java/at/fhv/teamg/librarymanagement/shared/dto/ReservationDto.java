package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class ReservationDto implements Dto, Serializable {
    private static final long serialVersionUID = -8940871800405364694L;

    private final UUID id;
    private final LocalDate endDate;
    private final LocalDate startDate;
    private final UUID mediumId;
    private final String mediumName;
    private final UUID userId;
    private final String userName;

    private ReservationDto(ReservationDtoBuilder reservationDtoBuilder) {
        this.id = reservationDtoBuilder.id;
        this.endDate = reservationDtoBuilder.endDate;
        this.startDate = reservationDtoBuilder.startDate;
        this.mediumId = reservationDtoBuilder.mediumId;
        this.mediumName = reservationDtoBuilder.mediumName;
        this.userId = reservationDtoBuilder.userId;
        this.userName = reservationDtoBuilder.userName;
    }

    public static class ReservationDtoBuilder {
        private UUID id;
        private LocalDate endDate;
        private LocalDate startDate;
        private UUID mediumId;
        private String mediumName;
        private UUID userId;
        private String userName;

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

        public ReservationDtoBuilder mediumName(String mediumName) {
            this.mediumName = mediumName;
            return this;
        }

        public ReservationDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public ReservationDtoBuilder userName(String userName) {
            this.userName = userName;
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

    public String getMediumName() {
        return mediumName;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return userName;
    }
}
