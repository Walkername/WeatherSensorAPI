package ru.detector.WeatherSensorAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.detector.WeatherSensorAPI.models.Sensor;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByName(String name);

}
