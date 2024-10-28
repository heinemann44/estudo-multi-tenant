package br.com.will.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private Long id;
    private String brand;
    private String model;
    private Long year;
    private String engineType;
    private String transmissionType;

}
