package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DvdDto implements Dto, Serializable {
    private static final long serialVersionUID = -6089310114540090564L;

    /* Medium properties */
    private final UUID id;
    private final LocalDate releaseDate;
    private final String storageLocation;
    private final String title;
    private final UUID topic;
    private final List<String> tags;
    private final String availability;
    private final UUID mediumId;


    /* DVD properties */
    private final String actors;
    private final String ageRestriction;
    private final String durationMinutes;
    private final String studio;
    private final String director;

    private DvdDto(DvdDtoBuilder dvdDtoBuilder) {
        this.id = dvdDtoBuilder.id;
        this.releaseDate = dvdDtoBuilder.releaseDate;
        this.storageLocation = dvdDtoBuilder.storageLocation;
        this.title = dvdDtoBuilder.title;
        this.topic = dvdDtoBuilder.topic;
        this.tags = dvdDtoBuilder.tags;
        this.availability = dvdDtoBuilder.availability;

        this.actors = dvdDtoBuilder.actors;
        this.ageRestriction = dvdDtoBuilder.ageRestriction;
        this.durationMinutes = dvdDtoBuilder.durationMinutes;
        this.studio = dvdDtoBuilder.studio;
        this.director = dvdDtoBuilder.director;
        this.mediumId = dvdDtoBuilder.mediumId;
    }

    public static class DvdDtoBuilder {
        private UUID id;
        private LocalDate releaseDate;
        private String storageLocation;
        private String title;
        private UUID topic;
        private List<String> tags;
        private String availability;

        private String actors;
        private String ageRestriction;
        private String durationMinutes;
        private String studio;
        private String director;
        private UUID mediumId;


        public DvdDtoBuilder() {
            // GUI might not be able to provide an id
        }

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

        public DvdDtoBuilder topic(UUID topic) {
            this.topic = topic;
            return this;
        }

        public DvdDtoBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public DvdDtoBuilder availability(String availability) {
            this.availability = availability;
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

        public DvdDtoBuilder studio(String studio) {
            this.studio = studio;
            return this;
        }

        public DvdDtoBuilder director(String director) {
            this.director = director;
            return this;
        }

        public DvdDtoBuilder mediumId(UUID mediumId) {
            this.mediumId = mediumId;
            return this;
        }

        public DvdDto build() {
            return new DvdDto(this);
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

    public UUID getTopic() {
        return this.topic;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getAvailability() {
        return this.availability;
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

    public String getStudio() {
        return this.studio;
    }

    public String getDirector() {
        return this.director;
    }

    public UUID getMediumId() {
        return mediumId;
    }
}
