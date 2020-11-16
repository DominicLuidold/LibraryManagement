package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.domain.common.Utils;
import at.fhv.teamg.librarymanagement.server.persistance.dao.LendingDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.MediumCopyDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class LendingService extends BaseMediaService {
    private static final int EXTENDING_DURATION_IN_DAYS = 14;
    private static final int MAX_RENEWAL_COUNT = 2;

    /**
     * Create new lending.
     *
     * @param lendingDto Dto without id and dates
     * @return Newly created lending
     */
    public Optional<LendingDto> createLending(LendingDto lendingDto) {
        Optional<MediumCopy> mediumCopy = findMediumCopyById(lendingDto.getMediumCopyId());
        if (mediumCopy.isEmpty()) {
            return Optional.empty();
        }

        if (!mediumCopy.get().isAvailable()) {
            return Optional.empty();
        }

        Optional<User> user = findUserById(lendingDto.getUserId());
        if (user.isEmpty()) {
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

        if (updateLending(lending).isEmpty()) {
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
        // TODO
        //  - use code from this::extendLending() once interface changes to avoid code duplication
        Optional<MediumCopy> mediumCopyOptional = findMediumCopyById(mediumCopyDto.getId());
        if (mediumCopyOptional.isEmpty()) {
            return false;
        }

        MediumCopy mediumCopy = mediumCopyOptional.get();
        if (mediumCopy.isAvailable()) {
            return false;
        }

        Optional<Lending> lendingOptional = getCurrentLending(mediumCopy.getLending());
        if (lendingOptional.isEmpty()) {
            return false;
        }

        Lending lending = lendingOptional.get();
        lending.setReturnDate(LocalDate.now());
        if (updateLending(lending).isEmpty()) {
            return false;
        }

        mediumCopy.setAvailable(true);
        if (updateMediumCopy(mediumCopy).isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Extends a currently ongoing lending by the {@link #EXTENDING_DURATION_IN_DAYS} if
     * the renewal count hasn't exceeded {@link #MAX_RENEWAL_COUNT}.
     *
     * @param mediumCopyDto The medium copy to extend the lend for
     * @return {@link MessageDto} containing the status of the operation and a message
     */
    public MessageDto<EmptyDto> extendLending(MediumCopyDto mediumCopyDto) {
        Optional<MediumCopy> mediumCopyOptional = findMediumCopyById(mediumCopyDto.getId());
        if (mediumCopyOptional.isEmpty()) {
            return Utils.createMessageResponse(
                "Could not find specified medium copy",
                MessageDto.MessageType.FAILURE
            );
        }

        MediumCopy mediumCopy = mediumCopyOptional.get();
        if (mediumCopy.isAvailable()) {
            return Utils.createMessageResponse(
                "Selected medium copy is available and not currently on loan from anyone",
                MessageDto.MessageType.FAILURE
            );
        }

        if (mediumCopy.getMedium().getReservations().size() > 0) {
            return Utils.createMessageResponse(
                "Cannot extend medium copy, there are reservations for the medium",
                MessageDto.MessageType.FAILURE
            );
        }

        Optional<Lending> lendingOptional = getCurrentLending(mediumCopy.getLending());
        if (lendingOptional.isEmpty()) {
            return Utils.createMessageResponse(
                "Cannot find current lending to extend",
                MessageDto.MessageType.ERROR
            );
        }

        Lending lending = lendingOptional.get();
        if (lending.getRenewalCount() >= MAX_RENEWAL_COUNT) {
            return Utils.createMessageResponse(
                "Medium copy has been extended two times already",
                MessageDto.MessageType.FAILURE
            );
        }

        lending.setRenewalCount(lending.getRenewalCount() + 1);
        lending.setEndDate(lending.getEndDate().plusDays(EXTENDING_DURATION_IN_DAYS));
        if (updateLending(lending).isEmpty()) {
            return Utils.createMessageResponse(
                "Updating lending information has failed",
                MessageDto.MessageType.ERROR
            );
        }

        return Utils.createMessageResponse(
            "Lending extended successfully",
            MessageDto.MessageType.SUCCESS
        );
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
