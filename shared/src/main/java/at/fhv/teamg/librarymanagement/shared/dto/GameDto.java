package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GameDto implements Serializable {
    private static final long serialVersionUID = 5478353807950770613L;

    /* Medium properties */
    private final UUID id;
    private final LocalDate releaseDate;
    private final String storageLocation;
    private final String title;
    private final String topic;
    private final List<String> tags;
    private final String availability;

    /* Game properties */
    private final String publisher;
    private final String developer;
    private final String ageRestriction;
    private final String platforms;

    private GameDto(GameDtoBuilder gameDtoBuilder) {
        this.id = gameDtoBuilder.id;
        this.releaseDate = gameDtoBuilder.releaseDate;
        this.storageLocation = gameDtoBuilder.storageLocation;
        this.title = gameDtoBuilder.title;
        this.topic = gameDtoBuilder.topic;
        this.tags = gameDtoBuilder.tags;
        this.availability = gameDtoBuilder.availability;

        this.publisher = gameDtoBuilder.publisher;
        this.developer = gameDtoBuilder.developer;
        this.ageRestriction = gameDtoBuilder.ageRestriction;
        this.platforms = gameDtoBuilder.platforms;
    }

    public static class GameDtoBuilder {
        private UUID id;
        private LocalDate releaseDate;
        private String storageLocation;
        private String title;
        private String topic;
        private List<String> tags;
        private String availability;

        private String publisher;
        private String developer;
        private String ageRestriction;
        private String platforms;

        public GameDtoBuilder() {
            // GUI might not be able to provide an id
        }

        public GameDtoBuilder(UUID id) {
            this.id = id;
        }

        public GameDtoBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public GameDtoBuilder storageLocation(String storageLocation) {
            this.storageLocation = storageLocation;
            return this;
        }

        public GameDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public GameDtoBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public GameDtoBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public GameDtoBuilder availability(String availability) {
            this.availability = availability;
            return this;
        }

        public GameDtoBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public GameDtoBuilder developer(String developer) {
            this.developer = developer;
            return this;
        }

        public GameDtoBuilder ageRestriction(String ageRestriction) {
            this.ageRestriction = ageRestriction;
            return this;
        }

        public GameDtoBuilder platforms(String platforms) {
            this.platforms = platforms;
            return this;
        }

        public GameDto build() {
            return new GameDto(this);
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

    public List<String> getTags() {
        return this.tags;
    }

    public String getAvailability() {
        return availability;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public String getAgeRestriction() {
        return this.ageRestriction;
    }

    public String getPlatforms() {
        return this.platforms;
    }
}
