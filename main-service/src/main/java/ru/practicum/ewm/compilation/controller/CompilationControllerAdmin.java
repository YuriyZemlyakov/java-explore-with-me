package ru.practicum.ewm.compilation.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;

@RestController
@RequestMapping("/admin/compilations")
@AllArgsConstructor
public class CompilationControllerAdmin {
    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return service.saveCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId,
                                            @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        return service.updateCompilation(compId, updateCompilationRequest);
    }
}