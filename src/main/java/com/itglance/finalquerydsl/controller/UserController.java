package com.itglance.finalquerydsl.controller;


import com.itglance.finalquerydsl.model.QUser;
import com.itglance.finalquerydsl.model.User;
import com.itglance.finalquerydsl.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends BaseController<User, UserRepository> {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        super(User.class, QUser.user, userRepository);
        this.userRepository = userRepository;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public Iterable<User> findAllByWebQuerydsl(@QuerydslPredicate Predicate predicate) {
        return userRepository.findAll(predicate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<User> search(@QuerydslPredicate @RequestParam(value = "search") String search) {
        return super.search(search);
    }



}
