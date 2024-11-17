package ru.practicum.ewm.category;

public interface CategoryService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);
    CategoryDto editCategory(CategoryDto categoryDto);
    void deleteCategory(long categoryId);
}
