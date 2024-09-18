package lesniak.marta.habitTracker.service;

import lesniak.marta.habitTracker.entity.User;
import lesniak.marta.habitTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserById(Integer id) {

        return userRepository.findById(id).orElse(null);
    }

}
