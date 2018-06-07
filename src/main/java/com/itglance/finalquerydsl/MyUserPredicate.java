package com.itglance.finalquerydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Optional;

public class MyUserPredicate {

    public static Optional<BooleanExpression> getPredicate(SearchCriteria criteria) {
        PathBuilder<User> entityPath = new PathBuilder<>(User.class, "user");

        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                    return Optional.of(path.eq(value));
                case ">":
                    return Optional.of(path.goe(value));
                case "<":
                    return Optional.of(path.loe(value));
            }
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return Optional.of(path.eq(criteria.getValue().toString()));}
             if (criteria.getOperation().equalsIgnoreCase("~")) {
                return Optional.of(path.containsIgnoreCase(criteria.getValue().toString()));
            }

            }

        return Optional.empty();
    }

    private static boolean isNumeric(String s) {

        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}