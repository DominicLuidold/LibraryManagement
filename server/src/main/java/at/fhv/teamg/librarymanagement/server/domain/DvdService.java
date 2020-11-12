package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
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
                .title(dvd.getMedium().getTitle())
                .storageLocation(dvd.getMedium().getStorageLocation())
                .director(dvd.getDirector())
                .availability(getAvailability(dvd.getMedium()))
                .topic(dvd.getMedium().getTopic().getId());

            dtoList.add(builder.build());
        });

        return dtoList;
    }

    /**
     * Get all dvds from cache.
     *
     * @return all books
     */
    public List<DvdDto> getAllDvds() {
        List<DvdDto> dvdDtos = new LinkedList<>();

        getAll().forEach(dvd -> {
            DvdDto.DvdDtoBuilder builder = new DvdDto.DvdDtoBuilder(dvd.getId())
                .title(dvd.getMedium().getTitle())
                .storageLocation(dvd.getMedium().getStorageLocation())
                .director(dvd.getDirector())
                .availability(getAvailability(dvd.getMedium()))
                .topic(dvd.getMedium().getTopic().getId());

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
