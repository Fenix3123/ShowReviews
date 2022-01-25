package com.coderscampus.showreviews.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.showreviews.domain.Authorities;
import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.User;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long>{
 
	List<Movies> findByUsers(User user);
}
