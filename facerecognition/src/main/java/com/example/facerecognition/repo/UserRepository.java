package com.example.facerecognition.repo;

import com.example.facerecognition.model.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDetails,String> {

}
