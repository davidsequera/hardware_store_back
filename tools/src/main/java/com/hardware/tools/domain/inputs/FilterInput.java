package com.hardware.tools.domain.inputs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FilterInput {
    public String tool_field;
    public String tool_value;
    @Override
    public String toString() {
        return "Filter{" +
                "tool_field='" + tool_field + '\'' +
                ", tool_value='" + tool_value + '\'' +
                '}';
    }
}
