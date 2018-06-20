package com.itglance.finalquerydsl.predicate;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import java.util.Optional;

public class UserPredicate<T extends EntityPathBase> {

    private Class<T> classType;
    private final PathMetadata metaData;

    UserPredicate(Class parameterizedClass, EntityPathBase entityPathBase) {

        this.metaData = entityPathBase.getMetadata();
        this.classType = parameterizedClass;
    }

    public static UserPredicate forClass(Class parameterizedClass, EntityPathBase entityPathBase) {
        return new UserPredicate(parameterizedClass, entityPathBase);
    }

    public Optional<BooleanExpression> getPredicate(SearchCriteria criteria) {

        PathBuilder<T> entityPath = new PathBuilder<>(classType, metaData);

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
                return Optional.of(path.eq(criteria.getValue().toString()));
            }
            if (criteria.getOperation().equalsIgnoreCase("~")) {
                return Optional.of(path.startsWith(criteria.getValue().toString()));
            }

        }

        return Optional.empty();
    }

    private static boolean isNumeric(String s) {

        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}