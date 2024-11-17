package ru.practicum.ewm.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam(required = false) Collection<Long> ids,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getUsers(ids,from, size);
    }
    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserShortDto dto) {
        return service.addUser(dto);
    }
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        service.deleteUser(userId);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDuplicatedEmailError(DataIntegrityViolationException e) {
        return Map.of("error", "Такой email уже зарегистрирован");
    }
}
