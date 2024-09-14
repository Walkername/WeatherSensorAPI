package ru.detector.WeatherSensorAPI.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.detector.WeatherSensorAPI.dto.SensorDTO;
import ru.detector.WeatherSensorAPI.models.Sensor;
import ru.detector.WeatherSensorAPI.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Sensor findOne(int id) {
        Optional<Sensor> sensor = sensorsRepository.findById(id);
        return sensor.orElse(null);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

}
