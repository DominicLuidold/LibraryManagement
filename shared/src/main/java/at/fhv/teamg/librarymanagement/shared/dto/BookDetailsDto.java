package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;
import java.util.List;

public class BookDetailsDto implements Dto, Serializable {
    private static final long serialVersionUID = 7234119134192614849L;

    /* Medium properties */
    private final BookDto details;
    private final List<MediumCopyDto> copies;
    private final List<ReservationDto> reservations;

    private BookDetailsDto(BookDetailsDtoBuilder bookDtoBuilder) {
        this.details = bookDtoBuilder.bookDto;
        this.copies = bookDtoBuilder.copies;
        this.reservations = bookDtoBuilder.reservations;
    }

    public static class BookDetailsDtoBuilder {
        private BookDto bookDto;
        private List<MediumCopyDto> copies;
        private List<ReservationDto> reservations;

        public BookDetailsDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public BookDetailsDtoBuilder details(BookDto bookDto) {
            this.bookDto = bookDto;
            return this;
        }

        public BookDetailsDtoBuilder copies(List<MediumCopyDto> copies) {
            this.copies = copies;
            return this;
        }

        public BookDetailsDtoBuilder reservations(List<ReservationDto> reservations) {
            this.reservations = reservations;
            return this;
        }

        public BookDetailsDto build() {
            return new BookDetailsDto(this);
        }
    }

    public BookDto getDetails() {
        return details;
    }

    public List<MediumCopyDto> getCopies() {
        return copies;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }
}
