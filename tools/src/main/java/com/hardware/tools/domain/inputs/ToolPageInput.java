package com.hardware.tools.domain.inputs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ToolPageInput {

    public int page;
    public int size;
    public String sort_name;
    public String sort_direction;

    @Override
    public String toString() {
        return "ToolPageInput{" +
                "page=" + page +
                ", size=" + size +
                ", sort_name='" + sort_name + '\'' +
                ", sort_direction='" + sort_direction + '\'' +
                '}';
    }
}
