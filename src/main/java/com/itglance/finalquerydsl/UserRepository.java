package com.itglance.finalquerydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

}
