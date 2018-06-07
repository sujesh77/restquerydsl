package com.itglance.finalquerydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public Iterable<User> findAllByWebQuerydsl(@QuerydslPredicate Predicate predicate) {
        return userRepository.findAll(predicate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<User> search(@RequestParam(value = "search") String search) {
        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|~)(\\w+?),");

            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return userRepository.findAll(exp);
    }


}
