package com.coderscampus.showreviews.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.Tvshows;
import com.coderscampus.showreviews.domain.User;
import com.coderscampus.showreviews.service.MoviesService;
import com.coderscampus.showreviews.service.TvshowsService;
import com.coderscampus.showreviews.service.UserService;


@Controller
public class DashboardController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/dashboard")
	public String getDashBoard(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("movie", new Movies());
		model.put("tvshow", new Tvshows());
		model.put("user", user);
		//movies
		user = userService.findById(user.getId());
		List<Movies> movieList = user.getMovies();	
		List<Tvshows> tvList = user.getTvshows();
		model.put("movieList", movieList);
		model.put("tvList", tvList);
		return "Dashboard";
	}
	
	@GetMapping("/dashboard2")
	public String getDashBoard2(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("movie", new Movies());
		model.put("tvshow", new Tvshows());
		model.put("user", user);
		//movies
		user = userService.findById(user.getId());
		List<Movies> movieList = user.getMovies();	
		List<Tvshows> tvList = user.getTvshows();
		model.put("movieList", movieList);
		model.put("tvList", tvList);
		return "Dashboard2";
	}
	
	
	
	
	
	
}
