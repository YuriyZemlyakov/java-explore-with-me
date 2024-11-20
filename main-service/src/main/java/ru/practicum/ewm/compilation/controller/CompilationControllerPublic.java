package ru.practicum.ewm.compilation.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.model.CompilationDto;

import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@AllArgsConstructor
public class CompilationControllerPublic {
    private final CompilationService service;

    @GetMapping
    public Collection<CompilationDto> getCompilations(@RequestParam(required = false) boolean pinned,
                                                      @RequestParam(defaultValue = "0") int from,
                                                      @RequestParam(defaultValue = "10") int size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        return service.getCompilation(compId);
    }

}
