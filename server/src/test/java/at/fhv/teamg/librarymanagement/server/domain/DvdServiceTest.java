package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class DvdServiceTest {
    private static final UUID validMediumId = UUID.randomUUID();
    private static final UUID notValidMediumId = UUID.randomUUID();
    private static final UUID validMediumCopyId = UUID.randomUUID();
    private static final UUID notValidMediumCopyId = UUID.randomUUID();

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

        Topic topicEntityMock = mock(Topic.class);
        when(mediumMock.getTopic()).thenReturn(topicEntityMock);

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

    @Test
    void getAllDvds_shouldReturnList() {
        Dvd dvdMock = mock(Dvd.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        when(dvdMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);

        List<Dvd> dvdList = new LinkedList<>();
        dvdList.add(dvdMock);

        DvdService dvdService = spy(DvdService.class);
        doReturn(dvdList).when(dvdService).getAll();

        assertFalse(dvdService.getAllDvds().isEmpty());
    }

    @Test
    void getDvdByMediumId_shouldReturnDto() {
        Dvd dvdMock = mock(Dvd.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        when(dvdMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);
        when(mediumMock.getDvd()).thenReturn(dvdMock);

        DvdService dvdService = spy(DvdService.class);
        doReturn(Optional.of(mediumMock)).when(dvdService).findMediumById(validMediumId);

        assertTrue(dvdService.getDvdByMediumId(validMediumId).isPresent());
    }

    @Test
    void getDvdByMediumId_shouldReturnEmpty() {
        DvdService dvdService = spy(DvdService.class);
        doReturn(Optional.empty()).when(dvdService).findMediumById(notValidMediumId);
        assertFalse(dvdService.getDvdByMediumId(notValidMediumId).isPresent());
    }

    @Test
    void getDvdByMediumCopyId_shouldReturnDto() {
        Dvd dvdMock = mock(Dvd.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(dvdMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);
        when(mediumMock.getDvd()).thenReturn(dvdMock);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);

        DvdService dvdService = spy(DvdService.class);
        doReturn(Optional.of(mediumCopyMock)).when(dvdService)
            .findMediumCopyById(validMediumCopyId);

        assertTrue(dvdService.getDvdByMediumCopyId(validMediumCopyId).isPresent());
    }

    @Test
    void getDvdByMediumCopyId_shouldReturnEmpty() {
        DvdService dvdService = spy(DvdService.class);
        doReturn(Optional.empty()).when(dvdService).findMediumCopyById(notValidMediumCopyId);
        assertFalse(dvdService.getDvdByMediumCopyId(notValidMediumCopyId).isPresent());
    }
}
