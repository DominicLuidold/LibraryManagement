package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;
import java.util.List;

public class DvdDetailsDto implements Dto, Serializable {
    private static final long serialVersionUID = 7234119134192614849L;

    /* Medium properties */
    private final DvdDto details;
    private final List<MediumCopyDto> copies;
    private final List<ReservationDto> reservations;

    private DvdDetailsDto(DvdDetailsDtoBuilder bookDtoBuilder) {
        this.details = bookDtoBuilder.dvdDto;
        this.copies = bookDtoBuilder.copies;
        this.reservations = bookDtoBuilder.reservations;
    }

    public static class DvdDetailsDtoBuilder {
        private DvdDto dvdDto;
        private List<MediumCopyDto> copies;
        private List<ReservationDto> reservations;

        public DvdDetailsDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public DvdDetailsDtoBuilder details(DvdDto bookDto) {
            this.dvdDto = bookDto;
            return this;
        }

        public DvdDetailsDtoBuilder copies(List<MediumCopyDto> copies) {
            this.copies = copies;
            return this;
        }

        public DvdDetailsDtoBuilder reservations(List<ReservationDto> reservations) {
            this.reservations = reservations;
            return this;
        }

        public DvdDetailsDto build() {
            return new DvdDetailsDto(this);
        }
    }

    public DvdDto getDetails() {
        return details;
    }

    public List<MediumCopyDto> getCopies() {
        return copies;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }
}
