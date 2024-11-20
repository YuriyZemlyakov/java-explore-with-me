package ru.practicum.ewm.category;

import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    Category newDtoToEntity(NewCategoryDto dto);

    Category dtoToEntity(CategoryDto dto);

    CategoryDto entityToDto(Category category);

}
