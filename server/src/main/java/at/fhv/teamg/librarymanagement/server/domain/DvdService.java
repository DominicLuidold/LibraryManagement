package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.domain.common.Searchable;
import at.fhv.teamg.librarymanagement.server.persistance.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DvdService extends BaseMediaService implements Searchable<DvdDto> {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DvdDto> search(DvdDto dvdDto) {
        String topic = "";
        if (dvdDto.getTopic() != null) {
            Optional<Topic> topicEntity = findTopicById(dvdDto.getTopic());

            if (topicEntity.isPresent()) {
                topic = topicEntity.get().getName();
            }
        }

        List<Dvd> entities = findBy(
            dvdDto.getTitle(),
            dvdDto.getDirector(),
            dvdDto.getReleaseDate(),
            topic
        );

        List<DvdDto> dtoList = new LinkedList<>();
        entities.forEach(dvd -> {
            DvdDto.DvdDtoBuilder builder = new DvdDto.DvdDtoBuilder(dvd.getId())
                .availability(getAvailability(dvd.getMedium()))
                .actors(dvd.getActors())
                .ageRestriction(dvd.getAgeRestriction())
                .durationMinutes(String.valueOf(dvd.getDurationMinutes()))
                .releaseDate(dvd.getMedium().getReleaseDate())
                .storageLocation(dvd.getMedium().getStorageLocation())
                .studio(dvd.getStudio())
                .director(dvd.getDirector())
                .title(dvd.getMedium().getTitle())
                .topic(dvd.getMedium().getTopic().getId())
                .mediumId(dvd.getMedium().getId());

            dtoList.add(builder.build());
        });

        return dtoList;
    }

    /**
     * Get all dvds.
     *
     * @return all books
     */
    public List<DvdDto> getAllDvds() {
        List<DvdDto> dvdDtos = new LinkedList<>();

        getAll().forEach(dvd -> {
            DvdDto.DvdDtoBuilder builder = new DvdDto.DvdDtoBuilder(dvd.getId())
                .availability(getAvailability(dvd.getMedium()))
                .actors(dvd.getActors())
                .ageRestriction(dvd.getAgeRestriction())
                .durationMinutes(String.valueOf(dvd.getDurationMinutes()))
                .releaseDate(dvd.getMedium().getReleaseDate())
                .storageLocation(dvd.getMedium().getStorageLocation())
                .studio(dvd.getStudio())
                .director(dvd.getDirector())
                .title(dvd.getMedium().getTitle())
                .topic(dvd.getMedium().getTopic().getId())
                .mediumId(dvd.getMedium().getId());

            dvdDtos.add(builder.build());
        });

        return dvdDtos;
    }

    protected List<Dvd> getAll() {
        return new DvdDao().getAll();
    }

    protected List<Dvd> findBy(String title, String director, LocalDate releaseDate, String topic) {
        return new DvdDao().findBy(title, director, releaseDate, topic);
    }
}
