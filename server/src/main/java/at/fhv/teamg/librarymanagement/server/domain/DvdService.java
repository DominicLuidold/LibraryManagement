package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistence.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DvdService extends BaseMediaService {

    /**
     * Finds a specific ({@link Dvd} provided information within the DTO.
     *
     * @param dvdDto The DTO containing the information to search for
     * @return A List of DTOs
     */
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
        entities.forEach(dvd -> dtoList.add(Utils.createDvdDto(
            dvd,
            getAvailability(dvd.getMedium())
        )));

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
            dvdDtos.add(Utils.createDvdDto(dvd, getAvailability(dvd.getMedium())));
        });

        return dvdDtos;
    }

    /**
     * Get Dvd by Medium Id.
     *
     * @param mediumId uuid
     * @return DvdDto
     */
    public Optional<DvdDto> getDvdByMediumId(UUID mediumId) {
        Optional<Medium> medium = findMediumById(mediumId);
        if (medium.isPresent()) {
            Dvd dvd = medium.get().getDvd();
            if (dvd != null) {
                return Optional.of(Utils.createDvdDto(dvd, getAvailability(dvd.getMedium())));
            }
        }
        return Optional.empty();
    }

    /**
     * Get Dvd by MediumCopyId.
     *
     * @param mediumCopyId uuid
     * @return DvdDto
     */
    public Optional<DvdDto> getDvdByMediumCopyId(UUID mediumCopyId) {
        Optional<MediumCopy> mediumCopy = findMediumCopyById(mediumCopyId);
        if (mediumCopy.isPresent()) {
            Dvd dvd = mediumCopy.get().getMedium().getDvd();
            if (dvd != null) {
                return Optional.of(Utils.createDvdDto(dvd, getAvailability(dvd.getMedium())));
            }
        }
        return Optional.empty();
    }

    protected List<Dvd> getAll() {
        return new DvdDao().getAll();
    }

    protected List<Dvd> findBy(String title, String director, LocalDate releaseDate, String topic) {
        return new DvdDao().findBy(title, director, releaseDate, topic);
    }
}
