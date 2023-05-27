package com.hardware.tools.domain.entities;

import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter @Setter
public class ToolPage {

    public List<Tool> tools;

    public Long page;
    public Long length;
    public Long pages;
    public Long total;

    private ToolPageInput input;
    private List<FilterInput> filters;

    private String query;

    public ToolPage(ToolPageInput input, List<FilterInput> filters) {
        this.input = input;
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "ToolPage{" +
                "tools=" + tools +
                ", length=" + length +
                ", page=" + page +
                ", pages=" + pages +
                ", total=" + total +
                ", input=" + input +
                ", filters=" + filters +
                ", query='" + query + '\'' +
                '}';
    }
}
