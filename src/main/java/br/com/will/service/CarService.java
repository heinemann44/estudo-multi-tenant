package br.com.will.service;

import java.util.List;

import br.com.will.dto.CarDTO;
import br.com.will.interceptor.WebServiceException;
import br.com.will.model.Car;
import br.com.will.repository.CarRepository;
import br.com.will.util.ObjectMapperUtils;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class CarService {

    @Inject
    CarRepository carRepository;

    @Transactional
    public void save(CarDTO carDTO) {
        Log.info("Running car save");

        if (carDTO == null) {
            throw new WebServiceException("You must inform some value");
        }

        Car car;
        if (carDTO.getId() != null) {
            car = carRepository.findById(carDTO.getId());

            if (car == null) {
                throw new WebServiceException("None car has been found", Status.NOT_FOUND);
            }
        } else {
            car = new Car();
        }

        car.setBrand(carDTO.getBrand());
        car.setEngineType(carDTO.getEngineType());
        car.setModel(carDTO.getModel());
        car.setTransmissionType(carDTO.getTransmissionType());
        car.setYear(carDTO.getYear());

        carRepository.persist(car);
    }

    public List<CarDTO> listAll() {
        Log.info("Running car listAll");

        return ObjectMapperUtils.mapAll(carRepository.listAll(Sort.by("year")), CarDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        Log.info("Running car delete");
        carRepository.deleteById(id);
    }

}
