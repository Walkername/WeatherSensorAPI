package ru.detector.WeatherSensorAPI.util;

public class SensorNotCreatedException extends RuntimeException {

    public SensorNotCreatedException(String msg) {
        super(msg);
    }

}
