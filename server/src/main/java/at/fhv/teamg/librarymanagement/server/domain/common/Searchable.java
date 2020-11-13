package at.fhv.teamg.librarymanagement.server.domain.common;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import java.util.List;

public interface Searchable<T> {

    /**
     * Finds a specific media type ({@link Book}, {@link Dvd} or {@link Game} based on
     * provided information within the DTO.
     *
     * @param dto The DTO containing the information to search for
     * @return A List of DTOs
     */
    List<T> search(T dto);
}
