package ru.practicum.ewm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Collection;

public interface UserStorage extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @Query(value = "select * from user_table u order by u.id offset ?1 limit ?2", nativeQuery = true)
    Collection<User> findWithPagination(int from, int size);

    Collection<User> findByIdIn(Collection<Long> ids);
}
