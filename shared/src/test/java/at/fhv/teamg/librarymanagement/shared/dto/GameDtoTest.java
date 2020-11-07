package at.fhv.teamg.librarymanagement.shared.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class GameDtoTest {

    @Test
    void gameDtoBuilder_shouldReturnValidDto() {
        UUID id = UUID.fromString("2401e527-4bd3-4b67-8641-21119a686ed1");
        String title = "Game of Life";
        String topic = "Life";
        List<String> tags = new LinkedList<>();
        tags.add("Test");
        String publisher = "The Game Company";
        String developer = "Axl Rose";
        LocalDate releaseDate = LocalDate.now();
        String storageLocation = "Not available";
        String platforms = "ALL";
        String ageRestriction = "FSK18";

        GameDto gameDto = new GameDto.GameDtoBuilder(id)
            .title(title)
            .topic(topic)
            .tags(tags)
            .publisher(publisher)
            .developer(developer)
            .releaseDate(releaseDate)
            .storageLocation(storageLocation)
            .platforms(platforms)
            .ageRestriction(ageRestriction)
            .build();

        assertSame(gameDto.getId(), id, "UUID should be the same");
        assertSame(gameDto.getTitle(), title, "title should be the same");
        assertSame(gameDto.getTopic(), topic, "topic should be the same");
        assertEquals(gameDto.getTags(), tags, "tags should be the same");
        assertSame(gameDto.getPublisher(), publisher, "publisher should be the same");
        assertSame(gameDto.getDeveloper(), developer, "developer should be the same");
        assertSame(gameDto.getReleaseDate(), releaseDate, "releaseDate should be the same");
        assertSame(
            gameDto.getStorageLocation(),
            storageLocation,
            "storageLocation should be the same"
        );
        assertSame(gameDto.getPlatforms(), platforms, "platforms should be the same");
        assertSame(
            gameDto.getAgeRestriction(),
            ageRestriction,
            "ageRestriction should be the same"
        );
    }
}
