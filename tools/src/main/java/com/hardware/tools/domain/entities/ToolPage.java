package com.hardware.tools.domain.entities;

import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
public class ToolPage {

    public List<Tool> tools;

    public Long length;
    public Long page;
    public Long total_pages;
    public Long total_tools;

    private @Setter @Getter ToolPageInput input;
    private @Setter @Getter FilterInput filter;

    public ToolPage(ToolPageInput input, FilterInput filter) {
        this.input = input;
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "ToolPage{" +
                "tools=" + tools +
                ", length=" + length +
                ", page=" + page +
                ", total_pages=" + total_pages +
                ", total_tools=" + total_tools +
                ", input=" + input +
                ", filter=" + filter +
                '}';
    }
}
