package at.fhv.teamg.librarymanagement.shared.dto;

import java.util.UUID;

public class MediumCopyDto {
    private final UUID id;
    private final boolean isAvailable;
    private final UUID mediumID;

    /**
     * Dto for Copy.
     * @param builder builds the DTO
     */
    public MediumCopyDto(CopyDtoBuilder builder) {
        this.id = builder.id;
        this.isAvailable = builder.isAvailable;
        this.mediumID = builder.mediumID;
    }

    public static class CopyDtoBuilder {
        private UUID id;
        private boolean isAvailable;
        private UUID mediumID;

        public CopyDtoBuilder(UUID id) {
            this.id = id;
        }

        public CopyDtoBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public CopyDtoBuilder mediumID(UUID mediumID) {
            this.mediumID = mediumID;
            return this;
        }

        /**
         * Build a new MediumCopyDto.
         * @return new MediumCopyDto
         */
        public MediumCopyDto build() {
            MediumCopyDto mediumCopyDto = new MediumCopyDto(this);
            validate(mediumCopyDto);
            return mediumCopyDto;
        }

        private void validate(MediumCopyDto mediumCopyDto) {
            //validate
        }
    }

    public UUID getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public UUID getMediumID() {
        return mediumID;
    }
}
