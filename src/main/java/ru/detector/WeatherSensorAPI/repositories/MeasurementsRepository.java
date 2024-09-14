package ru.detector.WeatherSensorAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.detector.WeatherSensorAPI.models.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
