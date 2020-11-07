package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class GameServiceTest {

    @Test
    void search_shouldReturnDtoList() {
        // Mock incoming DTO
        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getTitle()).thenReturn("Title");
        when(gameDtoMock.getDeveloper()).thenReturn("Developer");
        when(gameDtoMock.getPlatforms()).thenReturn("Platforms");
        when(gameDtoMock.getTopic()).thenReturn("Topic");

        // Mock returned entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getTitle()).thenReturn("Title");
        when(mediumMock.getStorageLocation()).thenReturn("Storage Location");

        Game gameMock = mock(Game.class);
        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(gameMock.getId()).thenReturn(UUID.randomUUID());

        List<Game> mockedEntities = new LinkedList<>();
        mockedEntities.add(gameMock);

        // Prepare GameService
        GameService gameService = new GameService();
        GameDao mockedDao = spy(GameDao.class);
        doReturn(mockedEntities).when(mockedDao)
            .findBy(anyString(), anyString(), anyString(), anyString());
        gameService.gameDao = mockedDao;

        // Assertions
        List<GameDto> result = gameService.search(gameDtoMock);

        assertFalse(result.isEmpty(), "Search result should not be an empty list");
        assertEquals(result.size(), 1, "Search result should contain one entry");
    }
}
