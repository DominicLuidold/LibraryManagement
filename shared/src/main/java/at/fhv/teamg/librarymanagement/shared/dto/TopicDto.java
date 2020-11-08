package at.fhv.teamg.librarymanagement.shared.dto;

import java.util.UUID;

public class TopicDto {
    private final UUID id;
    private final String name;

    /**
     * Dto for Topic.
     * @param builder builds the DTO
     */
    public TopicDto(TopicDtoBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;

    }

    public static class TopicDtoBuilder {
        private UUID id;
        private String name;

        public TopicDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public TopicDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Build a new TopicDto.
         * @return new TopicDto
         */
        public TopicDto build() {
            return (new TopicDto(this));
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}