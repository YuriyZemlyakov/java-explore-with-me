package ru.practicum.ewm.user;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getUsers(Collection<Long> ids, int from, int size);

    UserDto addUser(UserShortDto dto);

    void deleteUser(long id);

}
