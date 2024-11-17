package ru.practicum.ewm.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryStorage storage;
    private final CategoryMapper mapper;
    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category cat = mapper.newDtoToEntity(newCategoryDto);
        return mapper.entityToDto(storage.save(cat));
    }

    @Override
    public CategoryDto editCategory(CategoryDto categoryDto) {
        Category cat = mapper.dtoToEntity(categoryDto);
        return mapper.entityToDto(storage.save(cat));
    }

    @Override
    public void deleteCategory(long categoryId) {
        storage.deleteById(categoryId);

    }
}
