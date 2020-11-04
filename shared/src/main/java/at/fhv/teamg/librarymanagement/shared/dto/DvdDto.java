package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class DvdDto implements Serializable {
    private static final long serialVersionUID = -6089310114540090564L;
    private final UUID id;
    private final LocalDate releaseDate;
    private final String storageLocation;
    private final String title;
    private final String topic;

    private final String actors;
    private final String ageRestriction;
    private final String durationMinutes;
    private final String director;


    private DvdDto(DvdDtoBuilder dvdDtoBuilder) {
        this.id = dvdDtoBuilder.id;
        this.releaseDate = dvdDtoBuilder.releaseDate;
        this.storageLocation = dvdDtoBuilder.storageLocation;
        this.title = dvdDtoBuilder.title;
        this.topic = dvdDtoBuilder.topic;

        this.actors = dvdDtoBuilder.actors;
        this.ageRestriction = dvdDtoBuilder.ageRestriction;
        this.durationMinutes = dvdDtoBuilder.durationMinutes;
        this.director = dvdDtoBuilder.director;
    }


    public static class DvdDtoBuilder {
        private UUID id;
        private LocalDate releaseDate;
        private String storageLocation;
        private String title;
        private String topic;

        private String actors;
        private String ageRestriction;
        private String durationMinutes;
        private String director;

        public DvdDtoBuilder(UUID id) {
            this.id = id;
        }

        public DvdDtoBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public DvdDtoBuilder storageLocation(String storageLocation) {
            this.storageLocation = storageLocation;
            return this;
        }

        public DvdDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public DvdDtoBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public DvdDtoBuilder actors(String actors) {
            this.actors = actors;
            return this;
        }

        public DvdDtoBuilder ageRestriction(String ageRestriction) {
            this.ageRestriction = ageRestriction;
            return this;
        }

        public DvdDtoBuilder durationMinutes(String durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public DvdDtoBuilder director(String director) {
            this.director = director;
            return this;
        }

        /**
         * Build a new DvdDto.
         * @return new DvdDto
         */
        public DvdDto build() {
            DvdDto dvdDto =  new DvdDto(this);
            validateDvdDto(dvdDto);
            return dvdDto;
        }

        private void validateDvdDto(DvdDto dvdDto) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
        }
    }

    public UUID getId() {
        return this.id;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public String getStorageLocation() {
        return this.storageLocation;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getActors() {
        return this.actors;
    }

    public String getAgeRestriction() {
        return this.ageRestriction;
    }

    public String getDurationMinutes() {
        return this.durationMinutes;
    }

    public String getDirector() {
        return this.director;
    }

    @Override
    public String toString() {
        return "DvdDto{"
                + "id=" + id
                + ", releaseDate=" + releaseDate
                + ", storageLocation='" + storageLocation + '\''
                + ", title='" + title + '\''
                + ", topic='" + topic + '\''
                + ", actors='" + actors + '\''
                + ", ageRestriction='" + ageRestriction + '\''
                + ", durationMinutes='" + durationMinutes + '\''
                + ", studio='" + director + '\''
                + '}';
    }
}