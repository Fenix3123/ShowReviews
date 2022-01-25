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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.User;
import com.coderscampus.showreviews.service.MoviesService;
import com.coderscampus.showreviews.service.UserService;


@Controller
public class MovieController {
	@Autowired
	private MoviesService moviesService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/movie")
	public String saveMovie(@AuthenticationPrincipal User user, Movies movie, String dateofwatch) {
		saveMoviesRef(user, movie, dateofwatch);
		return "redirect:/dashboard";
	}

	private void saveMoviesRef(User user, Movies movie, String dateofwatch) {
		LocalDate localDate = LocalDate.parse(dateofwatch);
		movie.setDate(localDate);
		user = userService.findById(user.getId());
		movie.getUsers().add(user);
		user.getMovies().add(movie);
		moviesService.saveMovies(movie);
	}
	
	@PostMapping("/{userId}/movie/{movieId}/delete")
	public String deleteOneMovie (@PathVariable Long movieId, @PathVariable Long userId) {
		User user = userService.findById(userId);
		user.setMovies(user.getMovies().stream()
									   .filter(movie ->{
										   String str1 = String.valueOf(movie.getId());
										   String str2 = String.valueOf(movieId);
										   return !str1.equals(str2);
									   })
									   .collect(Collectors.toList()));
		userService.saveUser(user);
		return "redirect:/dashboard";
	}
	
	@PostMapping("/deleteMovie")
	@ResponseBody
	public String deleteOneMovie2 (@AuthenticationPrincipal User user, @RequestBody String moviename) {
		User user2 = userService.findById(user.getId());
		int namesize = moviename.length();
		String movieNameActual = moviename.substring(1, namesize-1);
		user2.setMovies(user2.getMovies().stream()
									   .filter(movie ->{
										   String str1 = String.valueOf(movie.getName());
										   String str2 = String.valueOf(movieNameActual);
										   return !str1.equals(str2);
									   })
									   .collect(Collectors.toList()));
		userService.saveUser(user2);
		return "/dashboard";
	}
	
	@GetMapping("/{userId}/movie/{movieId}")
	public String getMovie(ModelMap model, @PathVariable Long movieId, @AuthenticationPrincipal User user) {
		Movies movie = moviesService.findById(movieId);
		model.put("user", user);
		model.put("movieItem", movie);
		return "/movieview";
	}
	
	@PostMapping("/{userId}/movie/{movieId}")
	public String getMovie(Movies movie, String dateofwatch, @PathVariable Long userId) {
		LocalDate localDate = LocalDate.parse(dateofwatch);
		movie.setDate(localDate);
		moviesService.saveMovies(movie);
		return "redirect:/"+userId+"/movie/"+movie.getId();
	}
	
	@GetMapping("/getMovieList")
	@ResponseBody
	public List<Movies> getMovieList(@AuthenticationPrincipal User user) {
		user = userService.findById(user.getId());
		List <Movies> movieList = moviesService.findByUsers(user);
		return movieList;
	}
	
	
	@GetMapping("/movieview2")
	public String getMovieView2(ModelMap model) {
		model.put("movieItem", new Movies());
		return "movieview2";
	}
	
	@PostMapping("/movieview2")
	public String postMovieView2(@AuthenticationPrincipal User user, Movies movie, String dateofwatch) {
		saveMoviesRef(user, movie, dateofwatch);
		return "redirect:/dashboard";
	}
}
