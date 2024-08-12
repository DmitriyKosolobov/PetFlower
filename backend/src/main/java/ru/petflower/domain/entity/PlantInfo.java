package ru.petflower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plant_info")
@Getter
@Setter
@NoArgsConstructor
public class PlantInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    private String info;

    @OneToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;
}
