package com.hardware.tools.domain.inputs;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FilterInput {
    public String field;
    public List<String> values;

    @Override
    public String toString() {
        return "FilterInput{" +
                "field='" + field + '\'' +
                ", values=" + values +
                '}';
    }
}
