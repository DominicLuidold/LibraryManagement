package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class DvdServiceTest {

    @Test
    void search_shouldReturnDtoList() {
        // Mock incoming DTO
        DvdDto dvdDtoMock = mock(DvdDto.class);
        UUID topic = UUID.fromString("2401e527-4bd3-4b67-8641-deadbeef6ed1");
        when(dvdDtoMock.getTitle()).thenReturn("Title");
        when(dvdDtoMock.getReleaseDate()).thenReturn(LocalDate.of(2001, 8, 25));
        when(dvdDtoMock.getDirector()).thenReturn("Director");
        when(dvdDtoMock.getTopic()).thenReturn(topic);

        // Mock returned entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getTitle()).thenReturn("Title");
        when(mediumMock.getStorageLocation()).thenReturn("Storage Location");

        Dvd dvdMock = mock(Dvd.class);
        when(dvdMock.getMedium()).thenReturn(mediumMock);
        when(dvdMock.getId()).thenReturn(UUID.randomUUID());
        when(dvdMock.getDirector()).thenReturn("Director");

        List<Dvd> mockedEntities = new LinkedList<>();
        mockedEntities.add(dvdMock);

        // Prepare DvdService
        DvdService dvdService = spy(DvdService.class);
        Topic topicMock = mock(Topic.class);
        when(topicMock.getName()).thenReturn("Topic");
        doReturn(mockedEntities).when(dvdService)
            .findBy(anyString(), anyString(), any(LocalDate.class), anyString());
        doReturn(Optional.of(topicMock)).when(dvdService).findTopicById(any(UUID.class));

        // Assertions
        List<DvdDto> result = dvdService.search(dvdDtoMock);

        assertFalse(result.isEmpty(), "Search result should not be an empty list");
        assertEquals(result.size(), 1, "Search result should contain one entry");
    }
}
