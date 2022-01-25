package com.coderscampus.showreviews.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.Tvshows;
import com.coderscampus.showreviews.domain.User;

@Repository
public interface TvshowsRepository extends JpaRepository<Tvshows, Long>{
	
	List<Tvshows> findByUsers(User user);
}
