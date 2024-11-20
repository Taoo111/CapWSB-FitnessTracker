package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrainingServiceImpl {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public List<Training> getTrainingsByUser(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    public List<Training> getCompletedTrainingsAfterDate(LocalDate date) {
        LocalDateTime afterTime = date.atStartOfDay();
        return trainingRepository.findByCompletedTrueAndStartTimeAfter(afterTime);
    }

    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    public Training updateTraining(Long id, Training updatedTraining) {
        return trainingRepository.findById(id).map(existingTraining -> {
            existingTraining.setStartTime(updatedTraining.getStartTime());
            existingTraining.setEndTime(updatedTraining.getEndTime());
            existingTraining.setActivityType(updatedTraining.getActivityType());
            existingTraining.setDistance(updatedTraining.getDistance());
            existingTraining.setAverageSpeed(updatedTraining.getAverageSpeed());
            existingTraining.setCompleted(updatedTraining.isCompleted());
            return trainingRepository.save(existingTraining);
        }).orElseThrow(() -> new EntityNotFoundException("Training not found with ID: " + id));
    }
}
