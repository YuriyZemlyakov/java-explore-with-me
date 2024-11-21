package ru.practicum.ewm.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryStorage extends JpaRepository<Category, Long> {
}
