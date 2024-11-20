package ru.practicum.ewm.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage storage;
    private final UserMapper mapper;

    @Override
    @Transactional
    public Collection<UserDto> getUsers(Collection<Long> ids, int from, int size) {
        if (ids == null) {
            return storage.findWithPagination(from, size).stream()
                    .map(user -> mapper.entityToDto(user))
                    .collect(Collectors.toList());
        } else {
            return storage.findByIdIn(ids).stream()
                    .map(user -> mapper.entityToDto(user))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto addUser(UserShortDto userShortDto) {
        return mapper.entityToDto(storage.save(mapper.dtoToEntity(userShortDto)));
    }

    @Override
    public void deleteUser(long id) {
        storage.deleteById(id);

    }
}
