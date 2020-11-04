package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class LendingDto implements Serializable {
    private static final long serialVersionUID = 4488858801782386377L;
    private final UUID id;
    private final LocalDate endDate;
    private final Integer renewalCount;
    private final LocalDate returnDate;
    private final LocalDate startDate;
    private final UUID mediumCopyId;

    private LendingDto(LendingDtoBuilder lendingDtoBuilder) {
        this.id = lendingDtoBuilder.id;
        this.endDate = lendingDtoBuilder.endDate;
        this.renewalCount = lendingDtoBuilder.renewalCount;
        this.returnDate = lendingDtoBuilder.returnDate;
        this.startDate = lendingDtoBuilder.startDate;
        this.mediumCopyId = lendingDtoBuilder.mediumCopyId;
    }

    public static class LendingDtoBuilder {
        private UUID id;
        private LocalDate endDate;
        private Integer renewalCount;
        private LocalDate returnDate;
        private LocalDate startDate;
        private UUID mediumCopyId;

        public LendingDtoBuilder(UUID id) {
            this.id = id;
        }

        public LendingDtoBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public LendingDtoBuilder renewalCount(Integer renewalCount) {
            this.renewalCount = renewalCount;
            return this;
        }

        public LendingDtoBuilder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public LendingDtoBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public LendingDtoBuilder mediumCopyId(UUID mediumCopyId) {
            this.mediumCopyId = mediumCopyId;
            return this;
        }


        /**
         * Build a new LendingDto.
         *
         * @return new LendingDto
         */
        public LendingDto build() {
            LendingDto lendingDto = new LendingDto(this);
            validateLendingDto(lendingDto);
            return lendingDto;
        }

        private void validateLendingDto(LendingDto lendingDto) {
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

    public Integer getRenewalCount() {
        return this.renewalCount;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public UUID getmediumCopyId() {
        return this.mediumCopyId;
    }

    @Override
    public String toString() {
        return "LendingDto{"
                + "id=" + id
                + ", endDate=" + endDate
                + ", renewalCount=" + renewalCount
                + ", returnDate=" + returnDate
                + ", startDate=" + startDate
                + ", mediumCopyId=" + mediumCopyId
                + '}';
    }
}
