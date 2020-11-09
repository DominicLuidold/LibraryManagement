package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.LendingDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.MediumCopyDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import java.util.Optional;
import java.util.UUID;

public class LendingService extends BaseMediaService {
    /**
     * Create new lending.
     *
     * @param lendingDto Dto without id
     * @return Newly created lending
     */
    public Optional<LendingDto> createLending(LendingDto lendingDto) {
        Optional<MediumCopy> mediumCopy = findMediumCopyById(lendingDto.getMediumCopyId());
        if (!mediumCopy.isPresent()) {
            return Optional.empty();
        }

        if (!mediumCopy.get().isAvailable()) {
            return Optional.empty();
        }

        Optional<User> user = findUserById(lendingDto.getUserId());
        if (!user.isPresent()) {
            return Optional.empty();
        }

        Lending lending = new Lending();
        lending.setId(UUID.randomUUID());
        lending.setStartDate(lendingDto.getStartDate());
        lending.setEndDate(lendingDto.getEndDate());
        lending.setMediumCopy(mediumCopy.get());
        lending.setUser(user.get());
        lending.setRenewalCount(lendingDto.getRenewalCount());

        if (!updateLending(lending).isPresent()) {
            return Optional.empty();
        }

        MediumCopy copy = mediumCopy.get();
        copy.setAvailable(false);
        updateMediumCopy(copy);

        LendingDto.LendingDtoBuilder builder = new LendingDto.LendingDtoBuilder(lending.getId());
        builder.endDate(lending.getEndDate())
            .mediumCopyId(lending.getMediumCopy().getId())
            .renewalCount(lending.getRenewalCount())
            .returnDate(lending.getReturnDate())
            .startDate(lending.getStartDate())
            .userId(lending.getUser().getId());


        return Optional.of(builder.build());
    }

    protected Optional<MediumCopy> findMediumCopyById(UUID id) {
        MediumCopyDao dao = new MediumCopyDao();
        return dao.find(id);
    }

    protected Optional<Lending> updateLending(Lending lending) {
        LendingDao dao = new LendingDao();
        return dao.update(lending);
    }

    protected Optional<MediumCopy> updateMediumCopy(MediumCopy mediumCopy) {
        MediumCopyDao dao = new MediumCopyDao();
        return dao.update(mediumCopy);
    }
}
