package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.LendingDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.MediumCopyDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class LendingService extends BaseMediaService {
    /**
     * Create new lending.
     *
     * @param lendingDto Dto without id and dates
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
        lending.setStartDate(LocalDate.now());
        lending.setEndDate(
            LocalDate.now().plusDays(mediumCopy.get().getMedium().getType().getMaxLendingDays()));
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

    /**
     * Stop currently ongoing {@link Lending} (if any) for given {@link MediumCopy}.
     *
     * @param mediumCopyDto The MediumCopyDto to end the lending for
     * @return true if stopping the lending was successful, false otherwise
     */
    public boolean returnLending(MediumCopyDto mediumCopyDto) {
        Optional<MediumCopy> mediumCopyOptional = findMediumCopyById(mediumCopyDto.getId());
        if (!mediumCopyOptional.isPresent()) {
            return false;
        }

        MediumCopy mediumCopy = mediumCopyOptional.get();
        if (mediumCopy.isAvailable()) {
            return false;
        }

        Optional<Lending> lendingOptional = getCurrentLending(mediumCopy.getLending());
        if (!lendingOptional.isPresent()) {
            return false;
        }

        Lending lending = lendingOptional.get();
        lending.setReturnDate(LocalDate.now());
        if (!updateLending(lending).isPresent()) {
            return false;
        }

        mediumCopy.setAvailable(true);
        if (!updateMediumCopy(mediumCopy).isPresent()) {
            return false;
        }

        return true;
    }

    /**
     * Returns the currently ongoing lending (if any) based on given list of lending.
     *
     * @param lendingSet A list of lending
     * @return Current lending, {@link Optional#empty()} if there's no active lending
     */
    public Optional<Lending> getCurrentLending(Set<Lending> lendingSet) {
        for (Lending lending : lendingSet) {
            if ((lending.getStartDate().isBefore(LocalDate.now())
                || lending.getStartDate().isEqual(LocalDate.now()))
                && (lending.getEndDate().isAfter(LocalDate.now())
                || lending.getEndDate().isEqual(LocalDate.now()))
                && null == lending.getReturnDate()
            ) {
                return Optional.of(lending);
            }
        }

        return Optional.empty();
    }

    protected Optional<MediumCopy> findMediumCopyById(UUID id) {
        return new MediumCopyDao().find(id);
    }

    protected Optional<Lending> updateLending(Lending lending) {
        return new LendingDao().update(lending);
    }

    protected Optional<MediumCopy> updateMediumCopy(MediumCopy mediumCopy) {
        return new MediumCopyDao().update(mediumCopy);
    }
}
