package at.fhv.teamg.librarymanagement.client.controller.internal.media.util;

import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDropdown {

    private ArrayList<String> allUserString;
    private List<UserDto> allUserDtos;

    public UserDropdown(List<UserDto> allUserDtos) {
        this.allUserDtos = allUserDtos;
        createUsersString(allUserDtos);
    }

    private void createUsersString(List<UserDto> usersList) {
        allUserString = new ArrayList<>();
        for (UserDto user : usersList) {
            allUserString.add(String.format("%s (%s)", user.getName(), user.getUsername()));
        }
    }

    /**
     * returns userID to input String.
     * @param userName input string.
     * @return UUID of userName.
     */
    public UUID getUserID(String userName) {
        for (UserDto user : allUserDtos) {
            if ((user.getName() + " (" + user.getUsername() + ")").equals(userName)) {
                return user.getId();
            }
        }
        return null;
    }

    public ArrayList<String> getAllUserString() {
        return allUserString;
    }
}
