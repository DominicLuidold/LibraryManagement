package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import java.util.LinkedList;
import java.util.List;

public class DvdService extends BaseMediaService implements Searchable<DvdDto> {
    DvdDao dvdDao;

    public DvdService() {
        dvdDao = new DvdDao();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DvdDto> search(DvdDto dvdDto) {
        List<Dvd> entities = dvdDao.findBy(
            dvdDto.getTitle(),
            dvdDto.getDirector(),
            dvdDto.getReleaseDate(),
            dvdDto.getTopic()
        );

        List<DvdDto> dtoList = new LinkedList<>();
        entities.forEach(dvd -> {
            DvdDto.DvdDtoBuilder builder = new DvdDto.DvdDtoBuilder(dvd.getId())
                .title(dvd.getMedium().getTitle())
                .storageLocation(dvd.getMedium().getStorageLocation())
                .director(dvd.getDirector())
                .availability(getAvailability(dvd.getMedium()));

            dtoList.add(builder.build());
        });

        return dtoList;
    }
}