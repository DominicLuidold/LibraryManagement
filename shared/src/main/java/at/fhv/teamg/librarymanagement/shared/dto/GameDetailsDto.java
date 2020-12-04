package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;
import java.util.List;

public class GameDetailsDto implements Dto, Serializable {
    private static final long serialVersionUID = 7234119134192614849L;

    /* Medium properties */
    private final GameDto details;
    private final List<MediumCopyDto> copies;
    private final List<ReservationDto> reservations;

    private GameDetailsDto(GameDetailsDtoBuilder bookDtoBuilder) {
        this.details = bookDtoBuilder.gameDto;
        this.copies = bookDtoBuilder.copies;
        this.reservations = bookDtoBuilder.reservations;
    }

    public static class GameDetailsDtoBuilder {
        private GameDto gameDto;
        private List<MediumCopyDto> copies;
        private List<ReservationDto> reservations;

        public GameDetailsDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public GameDetailsDtoBuilder details(GameDto gameDto) {
            this.gameDto = gameDto;
            return this;
        }

        public GameDetailsDtoBuilder copies(List<MediumCopyDto> copies) {
            this.copies = copies;
            return this;
        }

        public GameDetailsDtoBuilder reservations(List<ReservationDto> reservations) {
            this.reservations = reservations;
            return this;
        }

        public GameDetailsDto build() {
            return new GameDetailsDto(this);
        }
    }

    public GameDto getDetails() {
        return details;
    }

    public List<MediumCopyDto> getCopies() {
        return copies;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }
}
