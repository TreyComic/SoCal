package com.SoCal.SoCal.controller;

import com.SoCal.SoCal.domain.TimeSlot;
import com.SoCal.SoCal.domain.User;
import com.SoCal.SoCal.domain.enums.DayOfWeek;
import com.SoCal.SoCal.service.TimeSlotService;
import com.SoCal.SoCal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private UserService userService;

    @PostMapping("/TimeSlot")
    public ResponseEntity<TimeSlot> createTimeSlot(@Valid @RequestBody TimeSlot timeSlot) {
        TimeSlot createdTimeSlot = timeSlotService.save(timeSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeSlot);
    }

    @DeleteMapping("/TimeSlot/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/TimeSlot/{id}")
    public ResponseEntity<TimeSlot> getTimeSlot(@PathVariable Long id) {
        TimeSlot timeSlot = timeSlotService.findById(id);
        if (timeSlot != null) {
            return ResponseEntity.ok(timeSlot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/TimeSlot/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable Long id, @Valid @RequestBody TimeSlot updatedTimeSlot) {
        TimeSlot updatedSlot = timeSlotService.update(id, updatedTimeSlot);
        if (updatedSlot != null) {
            return ResponseEntity.ok(updatedSlot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checkAvailability")
    public ResponseEntity<Boolean> checkUserAvailability(@RequestParam Long userId, @RequestParam DayOfWeek dayOfWeek, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        boolean isAvailable = timeSlotService.isUserAvailable(userId, dayOfWeek, startTime, endTime);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/users/{userId}/timeSlots")
    public ResponseEntity<List<TimeSlot>> getUserTimeSlots(@PathVariable Long userId) {
        List<TimeSlot> userTimeSlots = timeSlotService.findAllByUserId(userId);
        if (userTimeSlots.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(userTimeSlots);
        }
    }

    @GetMapping("/users/{userId}/timeSlots/{dayOfWeek}")
    public ResponseEntity<List<TimeSlot>> getUserTimeSlotsByDayOfWeek(@PathVariable Long userId, @ PathVariable DayOfWeek dayOfWeek) {
        List<TimeSlot> userTimeSlots = timeSlotService.findByUserIdAndDayOfWeek(userId, dayOfWeek);
        if (userTimeSlots.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(userTimeSlots);
        }
    }


    @GetMapping("/availableUsers")
    public ResponseEntity<List<User>> getAvailableUsers(@RequestParam DayOfWeek dayOfWeek, @RequestParam LocalTime startTime, @RequestParam LocalTime endTime) {
        List<User> availableUsers = timeSlotService.findUsersWithoutOverlappingTimeSlots(dayOfWeek, startTime, endTime);
        if (availableUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(availableUsers);
        }
    }
}
