package ru.detector.WeatherSensorAPI.util;

public class MeasurementNotCreatedException extends RuntimeException {

    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }

}
