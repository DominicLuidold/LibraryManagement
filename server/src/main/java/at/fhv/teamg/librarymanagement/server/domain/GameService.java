package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameService extends BaseMediaService implements Searchable<GameDto> {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameDto> search(GameDto gameDto) {
        String topic = "";
        if (gameDto.getTopic()  != null) {
            Optional<Topic> topicEntity = findTopicById(gameDto.getTopic());
            if (topicEntity.isPresent()) {
                topic = topicEntity.get().getName();
            }
        }

        List<Game> entities = findBy(
            gameDto.getTitle(),
            gameDto.getDeveloper(),
            gameDto.getPlatforms(),
            topic
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

    protected List<Game> findBy(String title, String developer, String platform, String topic) {
        return new GameDao().findBy(title, developer, platform, topic);
    }
}
