package com.SoCal.SoCal.repository;

import com.SoCal.SoCal.domain.User;
import com.SoCal.SoCal.domain.enums.DayOfWeek;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


}
