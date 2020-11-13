package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.domain.common.Searchable;
import at.fhv.teamg.librarymanagement.server.domain.common.Utils;
import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameService extends BaseMediaService implements Searchable<GameDto> {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameDto> search(GameDto gameDto) {
        String topic = "";
        if (gameDto.getTopic() != null) {
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
            dtoList.add(Utils.createGameDto(game, getAvailability(game.getMedium())));
        });

        return dtoList;
    }

    /**
     * Get all games.
     *
     * @return all games
     */
    public List<GameDto> getAllGames() {
        List<GameDto> gameDtos = new LinkedList<>();

        getAll().forEach(game -> {
            gameDtos.add(Utils.createGameDto(game, getAvailability(game.getMedium())));
        });

        return gameDtos;
    }

    /**
     * Get Game by Medium Id.
     *
     * @param mediumId uuid
     * @return GameDto
     */
    public Optional<GameDto> getGameByMediumId(UUID mediumId) {
        Optional<Medium> medium = findMediumById(mediumId);
        if (medium.isPresent()) {
            Game game = medium.get().getGame();
            if (game != null) {
                return Optional.of(Utils.createGameDto(game, getAvailability(game.getMedium())));
            }
        }
        return Optional.empty();
    }

    /**
     * Get Game by MediumCopyId.
     *
     * @param mediumCopyId uuid
     * @return GameDto
     */
    public Optional<GameDto> getGameByMediumCopyId(UUID mediumCopyId) {
        Optional<MediumCopy> mediumCopy = findMediumCopyById(mediumCopyId);
        if (mediumCopy.isPresent()) {
            Game game = mediumCopy.get().getMedium().getGame();
            if (game != null) {
                return Optional.of(Utils.createGameDto(game, getAvailability(game.getMedium())));
            }
        }
        return Optional.empty();
    }

    protected List<Game> getAll() {
        return new GameDao().getAll();
    }

    protected List<Game> findBy(String title, String developer, String platform, String topic) {
        return new GameDao().findBy(title, developer, platform, topic);
    }
}
