package ru.practicum.ewm.category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return service.addCategory(newCategoryDto);
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto editCategory(@PathVariable long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        return service.editCategory(categoryId, categoryDto);
    }

//    ToDo добавить проверку на наличие связанных событий
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long categoryId) {
        service.deleteCategory(categoryId);
    }

}
