package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class MediumCopyDto implements Serializable {
    private static final long serialVersionUID = 4488858901782386377L;

    private final UUID id;
    private final boolean isAvailable;
    private final LocalDate lendTill;
    private final UUID mediumID;

    private MediumCopyDto(MediumCopyDtoBuilder builder) {
        this.id = builder.id;
        this.isAvailable = builder.isAvailable;
        this.lendTill = builder.lendTill;
        this.mediumID = builder.mediumID;
    }

    public static class MediumCopyDtoBuilder {
        private UUID id;
        private boolean isAvailable;
        private LocalDate lendTill;
        private UUID mediumID;

        public MediumCopyDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public MediumCopyDtoBuilder(UUID id) {
            this.id = id;
        }

        public MediumCopyDtoBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public MediumCopyDtoBuilder lendTill(LocalDate lendTill) {
            this.lendTill = lendTill;
            return this;
        }

        public MediumCopyDtoBuilder mediumID(UUID mediumID) {
            this.mediumID = mediumID;
            return this;
        }

        public MediumCopyDto build() {
            return new MediumCopyDto(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public LocalDate getLendTill() {
        return lendTill;
    }

    public UUID getMediumID() {
        return mediumID;
    }
}
