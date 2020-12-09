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

import at.fhv.teamg.librarymanagement.server.persistence.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class GameServiceTest {
    private static final UUID validMediumId = UUID.randomUUID();
    private static final UUID notValidMediumId = UUID.randomUUID();
    private static final UUID validMediumCopyId = UUID.randomUUID();
    private static final UUID notValidMediumCopyId = UUID.randomUUID();

    @Test
    void search_shouldReturnDtoList() {
        // Mock incoming DTO
        GameDto gameDtoMock = mock(GameDto.class);
        UUID topic = UUID.fromString("2401e527-4bd3-4b67-8641-deadbeef6ed1");
        when(gameDtoMock.getTitle()).thenReturn("Title");
        when(gameDtoMock.getDeveloper()).thenReturn("Developer");
        when(gameDtoMock.getPlatforms()).thenReturn("Platforms");
        when(gameDtoMock.getTopic()).thenReturn(topic);

        // Mock returned entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getTitle()).thenReturn("Title");
        when(mediumMock.getStorageLocation()).thenReturn("Storage Location");

        Topic topicEntityMock = mock(Topic.class);
        when(mediumMock.getTopic()).thenReturn(topicEntityMock);

        Game gameMock = mock(Game.class);
        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(gameMock.getId()).thenReturn(UUID.randomUUID());

        List<Game> mockedEntities = new LinkedList<>();
        mockedEntities.add(gameMock);

        // Prepare GameService
        GameService gameService = spy(GameService.class);
        Topic topicMock = mock(Topic.class);
        when(topicMock.getName()).thenReturn("Topic");
        doReturn(mockedEntities).when(gameService)
            .findBy(anyString(), anyString(), anyString(), anyString());
        doReturn(Optional.of(topicMock)).when(gameService).findTopicById(any(UUID.class));

        // Assertions
        List<GameDto> result = gameService.search(gameDtoMock);

        assertFalse(result.isEmpty(), "Search result should not be an empty list");
        assertEquals(result.size(), 1, "Search result should contain one entry");
    }

    @Test
    void getAllGames_shouldReturnList() {
        Game gameMock = mock(Game.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);

        List<Game> gameList = new LinkedList<>();
        gameList.add(gameMock);

        GameService gameService = spy(GameService.class);
        doReturn(gameList).when(gameService).getAll();

        assertFalse(gameService.getAllGames().isEmpty());
    }

    @Test
    void getGameByMediumId_shouldReturnDto() {
        Game gameMock = mock(Game.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);
        when(mediumMock.getGame()).thenReturn(gameMock);

        GameService gameService = spy(GameService.class);
        doReturn(Optional.of(mediumMock)).when(gameService).findMediumById(validMediumId);

        assertTrue(gameService.getGameByMediumId(validMediumId).isPresent());
    }

    @Test
    void getGameByMediumId_shouldReturnEmpty() {
        GameService gameService = spy(GameService.class);
        doReturn(Optional.empty()).when(gameService).findMediumById(notValidMediumId);
        assertFalse(gameService.getGameByMediumId(notValidMediumId).isPresent());
    }

    @Test
    void getGameByMediumCopyId_shouldReturnDto() {
        Game gameMock = mock(Game.class);
        Medium mediumMock = mock(Medium.class);
        Topic topicMock = mock(Topic.class);
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getTopic()).thenReturn(topicMock);
        when(mediumMock.getGame()).thenReturn(gameMock);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);

        GameService gameService = spy(GameService.class);
        doReturn(Optional.of(mediumCopyMock)).when(gameService)
            .findMediumCopyById(validMediumCopyId);

        assertTrue(gameService.getGameByMediumCopyId(validMediumCopyId).isPresent());
    }

    @Test
    void getGameByMediumCopyId_shouldReturnEmpty() {
        GameService gameService = spy(GameService.class);
        doReturn(Optional.empty()).when(gameService).findMediumCopyById(notValidMediumCopyId);
        assertFalse(gameService.getGameByMediumCopyId(notValidMediumCopyId).isPresent());
    }
}
