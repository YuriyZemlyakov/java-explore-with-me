package ru.practicum.ewm.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryStorage storage;
    private final CategoryMapper mapper;
    private final EventStorage eventStorage;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category cat = mapper.newDtoToEntity(newCategoryDto);
        return mapper.entityToDto(storage.save(cat));
    }

    @Override
    public CategoryDto editCategory(long categoryId, CategoryDto categoryDto) {
        Category editedCat = storage.findById(categoryId).orElseThrow(() -> new NotFoundException(String
                .format("Категория %s не найдена", categoryId)));
        editedCat.setName(categoryDto.getName());
        return mapper.entityToDto(storage.save(editedCat));
    }

    @Override
    public void deleteCategory(long categoryId) {
        if (eventStorage.findAllByCategory_Id(categoryId).size() != 0) {
            throw new ConflictException("Есть события, имеющие такую категорию");
        }
        storage.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategory(long catId) {
        return mapper.entityToDto(storage.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория не найдена")));
    }

    @Override
    public Collection<CategoryDto> getCategories(int from, int size) {
        return storage.findAll().stream()
                .map(category -> mapper.entityToDto(category))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }
}
