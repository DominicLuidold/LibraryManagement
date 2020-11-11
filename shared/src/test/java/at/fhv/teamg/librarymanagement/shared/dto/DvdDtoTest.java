package at.fhv.teamg.librarymanagement.shared.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class DvdDtoTest {

    @Test
    void dvdDtoBuilder_shouldReturnValidDto() {
        UUID id = UUID.fromString("2401e527-4bd3-4b67-8641-21119a686ed1");
        String title = "The real Movie";
        UUID topic = UUID.fromString("2401e527-4bd3-4b67-8641-deadbeef6ed1");
        List<String> tags = new LinkedList<>();
        tags.add("Test");
        String director = "Fantasia";
        String storageLocation = "9 4/3";
        LocalDate releaseDate = LocalDate.now();
        String durationMinutes = "1337";
        String actors = "Jony, Axel";
        String ageRestriction = "FSK35";

        DvdDto dvdDto = new DvdDto.DvdDtoBuilder(id)
            .title(title)
            .topic(topic)
            .tags(tags)
            .director(director)
            .storageLocation(storageLocation)
            .releaseDate(releaseDate)
            .durationMinutes(durationMinutes)
            .actors(actors)
            .ageRestriction(ageRestriction)
            .build();

        assertSame(dvdDto.getId(), id, "UUID should be the same");
        assertSame(dvdDto.getTitle(), title, "title should be the same");
        assertSame(dvdDto.getTopic(), topic, "topic should be the same");
        assertEquals(dvdDto.getTags(), tags, "tags should be the same");
        assertSame(dvdDto.getDirector(), director, "studio should be the same");
        assertSame(
            dvdDto.getStorageLocation(),
            storageLocation,
            "storageLocation should be the same"
        );
        assertSame(dvdDto.getReleaseDate(), releaseDate, "releaseDate should be the same");
        assertSame(
            dvdDto.getDurationMinutes(),
            durationMinutes,
            "durationMinutes should be the same"
        );
        assertSame(dvdDto.getActors(), actors, "actors should be the same");
        assertSame(dvdDto.getAgeRestriction(), ageRestriction, "ageRestriction should be the same");
    }
}