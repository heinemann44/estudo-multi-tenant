package br.com.will.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_car")
public class Car {

    @Id
    @SequenceGenerator(name = "gen_car", sequenceName = "gen_car", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "gen_car")
    private Long id;

    @Column(name = "tx_brand", nullable = false)
    private String brand;

    @Column(name = "tx_model", nullable = false)
    private String model;

    @Column(name = "nr_year", nullable = false)
    private Long year;

    @Column(name = "tx_engine_type", nullable = false)
    private String engineType;

    @Column(name = "tx_transmission_type", nullable = false)
    private String transmissionType;

}
