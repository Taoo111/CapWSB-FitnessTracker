package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{userId}")
    public List<Training> getTrainingsByUser(@PathVariable Long userId) {
        return trainingService.getTrainingsByUser(userId);
    }

    @GetMapping("/finished/{afterTime}")
    public List<Training> getCompletedTrainingsAfterDate(@PathVariable String afterTime) {
        LocalDate date = LocalDate.parse(afterTime);
        return trainingService.getCompletedTrainingsAfterDate(date);
    }

    @GetMapping("/activityType")
    public List<Training> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType);
    }

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training created = trainingService.createTraining(training);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<Training> updateTraining(
            @PathVariable Long trainingId,
            @RequestBody Training training) {
        Training updated = trainingService.updateTraining(trainingId, training);
        return ResponseEntity.ok(updated);
    }
}
