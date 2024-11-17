package ru.practicum.ewm.category;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return service.addCategory(newCategoryDto);
    }

    @PatchMapping
    public CategoryDto editCategory(@RequestBody CategoryDto categoryDto) {
        return service.editCategory(categoryDto);
    }

//    ToDo добавить проверку на наличие связанных событий
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable long categoryId) {
        service.deleteCategory(categoryId);
    }

}
