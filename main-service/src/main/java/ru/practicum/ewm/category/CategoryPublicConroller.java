package ru.practicum.ewm.category;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryPublicConroller {
    private final CategoryService service;

    @GetMapping
    public Collection<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from ,
                                                 @RequestParam(defaultValue = "10") int size) {
        return service.getCategories(from, size);
    }
    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return service.getCategory(catId);
    }
}
