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
    private final UUID userId;

    private LendingDto(LendingDtoBuilder lendingDtoBuilder) {
        this.id = lendingDtoBuilder.id;
        this.endDate = lendingDtoBuilder.endDate;
        this.renewalCount = lendingDtoBuilder.renewalCount;
        this.returnDate = lendingDtoBuilder.returnDate;
        this.startDate = lendingDtoBuilder.startDate;
        this.mediumCopyId = lendingDtoBuilder.mediumCopyId;
        this.userId = lendingDtoBuilder.userId;
    }

    public static class LendingDtoBuilder {
        private UUID id;
        private LocalDate endDate;
        private Integer renewalCount;
        private LocalDate returnDate;
        private LocalDate startDate;
        private UUID mediumCopyId;
        private UUID userId;

        public LendingDtoBuilder() {
            // GUI might not be able to provide an id
        }

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

        public LendingDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public LendingDto build() {
            return new LendingDto(this);
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

    public UUID getMediumCopyId() {
        return this.mediumCopyId;
    }

    public UUID getUserId() {
        return userId;
    }
}
