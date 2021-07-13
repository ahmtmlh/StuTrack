package com.yeet.StuTrack.dao;


import com.yeet.StuTrack.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    List<User> findAllByUserType(User.UserType userType);
}
