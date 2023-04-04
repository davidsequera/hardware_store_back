package com.hardware.tools.domain.entities;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class ToolPage {

    public Mono<Long> length;
    public Flux<Tool> tools;

    public ToolPage(Mono<Long> length, Flux<Tool> tools) {
        this.length = length;
        this.tools = tools;
    }
}
