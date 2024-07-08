package com.SoCal.SoCal.repository;

import com.SoCal.SoCal.domain.TimeSlot;
import com.SoCal.SoCal.domain.User;
import com.SoCal.SoCal.domain.enums.DayOfWeek;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {

    @Query("SELECT ts FROM TimeSlot ts " + "WHERE ts.dayOfWeek = :dayOfWeek " + "AND ((ts.startTime < :endTime AND ts.endTime > :startTime) OR " + "(ts.startTime < :endTime AND ts.endTime > :endTime) OR " + "(ts.startTime >= :startTime AND ts.endTime <= :endTime))")
    List<TimeSlot> findOverlappingTimeSlots(@Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
    @Query("SELECT ts FROM TimeSlot ts WHERE ts.user.id = :userId AND ts.dayOfWeek = :dayOfWeek")
    List<TimeSlot> findByUserIdAndDayOfWeek(@Param("userId") Long userId, @Param("dayOfWeek") DayOfWeek dayOfWeek);

    List<TimeSlot> findByUserId(Long userId);

}