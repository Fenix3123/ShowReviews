package com.coderscampus.showreviews.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.Tvshows;
import com.coderscampus.showreviews.domain.User;
import com.coderscampus.showreviews.repositories.TvshowsRepository;

@Service
public class TvshowsService {
	@Autowired
	TvshowsRepository tvRepo;
	
	public Tvshows saveTvshows(Tvshows tv) {
		return tvRepo.save(tv);
	}
	
	public Tvshows findById(Long tvId) {
		Optional<Tvshows> tvOpt = tvRepo.findById(tvId);
		return tvOpt.orElse(new Tvshows());
	}

	public List<Tvshows> findByUsers(User user) {
		return tvRepo.findByUsers(user);
	}
}
