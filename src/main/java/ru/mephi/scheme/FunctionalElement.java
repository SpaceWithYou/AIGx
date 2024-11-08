package ru.mephi.scheme;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionalElement {
    @JsonIgnore
    private int[] inputs;   //Четные - обычные входы, нечетные - с отрицанием
    @JsonIgnore
    private int output;     //Четный - обычный выход, нечетный - с отрицанием
    private ElementType type;
}
