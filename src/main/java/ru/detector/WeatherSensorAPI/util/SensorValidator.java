package ru.detector.WeatherSensorAPI.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.detector.WeatherSensorAPI.dto.SensorDTO;
import ru.detector.WeatherSensorAPI.models.Sensor;
import ru.detector.WeatherSensorAPI.services.SensorsService;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorsService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "This sensor name is already in use");
        }
    }
}
