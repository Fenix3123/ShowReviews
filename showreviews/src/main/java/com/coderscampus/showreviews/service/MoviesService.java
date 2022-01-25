package com.coderscampus.showreviews.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.User;
import com.coderscampus.showreviews.repositories.MoviesRepository;

@Service
public class MoviesService {
	@Autowired
	MoviesRepository moviesRepo;
	
	public Movies saveMovies(Movies movie) {
		return moviesRepo.save(movie);
	}

	public List<Movies> findAll() {
		// TODO Auto-generated method stub
		return moviesRepo.findAll();
	}
	
	public Movies findById(Long movieId) {
		Optional<Movies> movieOpt = moviesRepo.findById(movieId);
		return movieOpt.orElse(new Movies());
	}
	
	public void delete(Long movieId) {
		moviesRepo.deleteById(movieId);
	}
	
	public List<Movies> findByUsers(User user){
		return moviesRepo.findByUsers(user);
	}
}
