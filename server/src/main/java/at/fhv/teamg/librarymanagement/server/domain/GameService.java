package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.LinkedList;
import java.util.List;

public class GameService extends BaseMediaService implements Searchable<GameDto> {
    GameDao gameDao;

    public GameService() {
        gameDao = new GameDao();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameDto> search(GameDto gameDto) {
        List<Game> entities = gameDao.findBy(
            gameDto.getTitle(),
            gameDto.getDeveloper(),
            gameDto.getPlatforms(),
            gameDto.getTopic()
        );

        List<GameDto> dtoList = new LinkedList<>();
        entities.forEach(game -> {
            GameDto.GameDtoBuilder builder = new GameDto.GameDtoBuilder(game.getId())
                .title(game.getMedium().getTitle())
                .storageLocation(game.getMedium().getStorageLocation())
                .platforms(game.getPlatforms())
                .availability(getAvailability(game.getMedium()));

            dtoList.add(builder.build());
        });

        return dtoList;
    }
}