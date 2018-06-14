package com.itglance.finalquerydsl;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends BaseResource {

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
        return search(search, User.class, QUser.user, userRepository);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/location")
    @ResponseBody
    public Iterable<Location> searchLocation(@RequestParam(value = "search") String search) {
        return search(search, Location.class, QLocation.location, locationRepository);

    }

}
