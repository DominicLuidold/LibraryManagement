package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class GameDto implements Serializable {
    private static final long serialVersionUID = 5478353807950770613L;
    private final UUID id;
    private final LocalDate releaseDate;
    private final String storageLocation;
    private final String title;
    private final String topic;

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

        private String publisher;
        private String developer;
        private String ageRestriction;
        private String platforms;

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

        /**
         * Build a new GameDto.
         * @return new GameDto
         */
        public GameDto build() {
            GameDto gameDto =  new GameDto(this);
            validateGameDto(gameDto);
            return gameDto;
        }

        private void validateGameDto(GameDto gameDto) {
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

    @Override
    public String toString() {
        return "GameDto{"
                + "id=" + id
                + ", releaseDate=" + releaseDate
                + ", storageLocation='" + storageLocation + '\''
                + ", title='" + title + '\''
                + ", topic='" + topic + '\''
                + ", publisher='" + publisher + '\''
                + ", developer='" + developer + '\''
                + ", ageRestriction='" + ageRestriction + '\''
                + ", platforms='" + platforms + '\''
                + '}';
    }
}
