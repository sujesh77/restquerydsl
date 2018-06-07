package com.itglance.finalquerydsl;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyUserPredicatesBuilder {
    private List<SearchCriteria> params;

    public MyUserPredicatesBuilder() {
        params = new ArrayList<>();
    }

    public MyUserPredicatesBuilder with(
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
            Optional<BooleanExpression> exp = MyUserPredicate.getPredicate(param);
            exp.ifPresent(predicates::add);
        }

        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;
    }
}