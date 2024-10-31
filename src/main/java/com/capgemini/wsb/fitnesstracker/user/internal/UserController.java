package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<BasicUserDto> getAllBasicUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        return userService.createUser(userMapper.toEntity(userDto));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/older/{date}")
    public List<UserDto> getUsersOlderThan(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<User> users = userService.findUsersOlderThan(date);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userMapper.toDto(user));
        }
        return userDtos;
    }

    @GetMapping("/email")
    public List<UserIdDto> searchUserByEmail(@RequestParam String email) {
        return userService.searchUsersByPartialEmail(email)
                .stream()
                .map(userMapper::IdsDto)
                .toList();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            User foundUser = userService.getUser(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
            User updatedUser = userMapper.toUpdateEntity(userDto, foundUser);
            return userService.updateUser(updatedUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot update user" + e.getMessage());
        }
    }
}