package com.itglance.finalquerydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseResource<T extends BaseModel, R extends JpaRepository<T, Long> & QuerydslPredicateExecutor<T>> {

    public Iterable<T> search(@RequestParam(value = "search") String search, Class<T> mClass, EntityPathBase entityPathBase, R repo) {
        MyUserPredicatesBuilder builder = MyUserPredicatesBuilder.forClass(mClass, entityPathBase);
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|~)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }

        BooleanExpression exp = builder.build();
        return repo.findAll(exp);
    }

}
