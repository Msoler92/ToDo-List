package com.example.todolist.helper;

import com.example.todolist.domain.Todo;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class TodoSpecifications {
    private TodoSpecifications(){}

    public static Specification<Todo> titleContained(Optional<String> optParam) {
        return(root, query, criteriaBuilder) -> optParam
                .map(param -> criteriaBuilder.like(root.get("title"), "%" + param + "%"))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Todo> usernameEquals(Optional<String> optParam) {
        return (root, query, criteriaBuilder) -> optParam
                .map(param -> criteriaBuilder.equal(root.join("user", JoinType.INNER)
                        .get("username"), param))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Todo> queryWithFilters(Optional<String> title, Optional<String> username) {
        return Specification
                .where(titleContained(title))
                .and(usernameEquals(username));
    }
}
