package ru.petflower.controller.responses.pet;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PetResponse (

        @JsonProperty(value = "pet_id")
        Long petId,

        @JsonProperty(value = "pet_name")
        String petName,

        @JsonProperty(value = "plant_name")
        String plantName,

        @JsonProperty(namespace = "plant_kind")
        String plantKind,

        String condition
) {

}
