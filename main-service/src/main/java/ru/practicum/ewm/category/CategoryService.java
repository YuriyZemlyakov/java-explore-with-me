package ru.practicum.ewm.category;

import java.util.Collection;

public interface CategoryService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);
    CategoryDto editCategory(long categoryId, CategoryDto categoryDto);
    void deleteCategory(long categoryId);
    CategoryDto getCategory(long categoryId);
    Collection<CategoryDto> getCategories(int from, int size);
}
