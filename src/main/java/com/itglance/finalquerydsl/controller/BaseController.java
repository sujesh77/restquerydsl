package com.itglance.finalquerydsl.controller;

import com.itglance.finalquerydsl.model.BaseModel;
import com.itglance.finalquerydsl.predicate.UserPredicatesBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//this is base class

public class BaseController<T extends BaseModel, R extends JpaRepository<T, Long> & QuerydslPredicateExecutor<T>> {

    private final static Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|~)(\\w+?),");

    final Class<T> mClass;
    final EntityPathBase entityPathBase;
    final R repo;

    public BaseController(Class<T> mClass, EntityPathBase entityPathBase, R repo) {
        this.mClass = mClass;
        this.entityPathBase = entityPathBase;
        this.repo = repo;
    }

    public Iterable<T> search(String search) {

        UserPredicatesBuilder builder = UserPredicatesBuilder.forClass(mClass, entityPathBase);
        if (search != null) {
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }

        BooleanExpression exp = builder.build();
        return repo.findAll(exp);
    }

}
