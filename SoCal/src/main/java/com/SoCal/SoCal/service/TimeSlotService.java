package com.SoCal.SoCal.service;

import com.SoCal.SoCal.domain.TimeSlot;
import com.SoCal.SoCal.domain.User;
import com.SoCal.SoCal.domain.enums.DayOfWeek;
import com.SoCal.SoCal.repository.TimeSlotRepository;
import com.SoCal.SoCal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private UserRepository userRepository;

    public TimeSlot save(TimeSlot timeSlot) {
        // Here you can add additional logic if needed before saving
        return timeSlotRepository.save(timeSlot);
    }

    public void deleteById(Long id){
        timeSlotRepository.deleteById(id);
    }

    public TimeSlot findById(Long id){
        return timeSlotRepository.findById(id).orElse(null);
    }

    public TimeSlot update(Long id, TimeSlot updatedTimeSlot) {
        Optional<TimeSlot> existingTimeSlotOptional = timeSlotRepository.findById(id);
        if (existingTimeSlotOptional.isPresent()) {
            TimeSlot existingTimeSlot = existingTimeSlotOptional.get();
            existingTimeSlot.setDayOfWeek(updatedTimeSlot.getDayOfWeek());
            existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
            existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
            existingTimeSlot.setDescription(updatedTimeSlot.getDescription());
            return timeSlotRepository.save(existingTimeSlot);
        } else {
            return null;
        }
    }


    public boolean isUserAvailable(Long userId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByUserIdAndDayOfWeek(userId, dayOfWeek);
        for (TimeSlot slot : timeSlots) {
            if (slot.getStartTime().isBefore(endTime) && slot.getEndTime().isAfter(startTime)) {
                return false; // Overlapping time slot found
            }
        }
        return true; // No overlapping time slots found
    }


    public List<TimeSlot> findAllByUserId(Long userId) {
        return timeSlotRepository.findByUserId(userId);
    }

    public List<TimeSlot> findByUserIdAndDayOfWeek(Long userId, DayOfWeek dayOfWeek) {
        return timeSlotRepository.findByUserIdAndDayOfWeek(userId, dayOfWeek);
    }

    public List<User> findUsersWithoutOverlappingTimeSlots(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        List<User> allUsers = (List<User>) userRepository.findAll();

        List<User> usersWithConflicts = timeSlotRepository.findOverlappingTimeSlots(dayOfWeek, startTime, endTime).stream().map(TimeSlot::getUser).distinct().collect(Collectors.toList());

        List<User> availableUsers = allUsers.stream().filter(user -> !usersWithConflicts.contains(user)).collect(Collectors.toList());

        return availableUsers;
    }
}