package com.itglance.finalquerydsl.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserPredicatesBuilder {
    private final Class classType;
    private final EntityPathBase entityPathBase;

    private List<SearchCriteria> params;

    UserPredicatesBuilder(Class parameterizedClass, EntityPathBase entityPathBase) {
        params = new ArrayList<>();
        this.classType = parameterizedClass;
        this.entityPathBase = entityPathBase;

    }

    public static UserPredicatesBuilder forClass(Class parameterizedClass, EntityPathBase entityPathBase) {
        return new UserPredicatesBuilder(parameterizedClass, entityPathBase);
    }

    public UserPredicatesBuilder with(
            String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.isEmpty()) {
            return null;
        }
        List<BooleanExpression> predicates = new ArrayList<>();
        for (SearchCriteria param : params) {
            Optional<BooleanExpression> exp = UserPredicate.forClass(classType, entityPathBase).getPredicate(param);
            exp.ifPresent(predicates::add);
        }

        Optional<BooleanExpression> expression = predicates.stream().reduce(BooleanExpression::and);

        return expression.get();
    }
}