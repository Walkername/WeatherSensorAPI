package ru.detector.WeatherSensorAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.detector.WeatherSensorAPI.dto.MeasurementDTO;
import ru.detector.WeatherSensorAPI.models.Measurement;
import ru.detector.WeatherSensorAPI.models.Sensor;
import ru.detector.WeatherSensorAPI.services.MeasurementsService;
import ru.detector.WeatherSensorAPI.services.SensorsService;
import ru.detector.WeatherSensorAPI.util.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final SensorsService sensorsService;
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(SensorsService sensorsService, MeasurementsService measurementsService, MeasurementValidator measurementValidator) {
        this.sensorsService = sensorsService;
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping("")
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount() {
        return measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @PostMapping("/add")
    private ResponseEntity<HttpStatus> add(
            @RequestBody @Valid MeasurementDTO measurementDTO,
            BindingResult bindingResult
    ) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        measurementsService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException ex) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
