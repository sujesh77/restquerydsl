package com.itglance.finalquerydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public UserController(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public Iterable<User> findAllByWebQuerydsl(@QuerydslPredicate Predicate predicate) {
        return userRepository.findAll(predicate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<User> search(@RequestParam(value = "search") String search) {
        MyUserPredicatesBuilder builder = MyUserPredicatesBuilder.forClass(User.class, QUser.user);
        checkPattern(search, builder);
        BooleanExpression exp = builder.build();
        return userRepository.findAll(exp);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/location")
    @ResponseBody
    public Iterable<Location> searchLocation(@RequestParam(value = "search") String search) {
        MyUserPredicatesBuilder builder = MyUserPredicatesBuilder.forClass(Location.class, QLocation.location);
        checkPattern(search, builder);
        BooleanExpression exp = builder.build();
        return locationRepository.findAll(exp);
    }

    private void checkPattern(@RequestParam(value = "search") String search, MyUserPredicatesBuilder builder) {
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|~)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
    }


}
