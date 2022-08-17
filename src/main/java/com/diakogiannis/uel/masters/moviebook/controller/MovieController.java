package com.diakogiannis.uel.masters.moviebook.controller;

import com.diakogiannis.uel.masters.moviebook.enums.LikeEnum;
import com.diakogiannis.uel.masters.moviebook.enums.SortByEnum;
import com.diakogiannis.uel.masters.moviebook.enums.UrlBindingsEnum;
import com.diakogiannis.uel.masters.moviebook.model.dto.MovieDTO;
import com.diakogiannis.uel.masters.moviebook.model.dto.MovieRatingDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Movie;
import com.diakogiannis.uel.masters.moviebook.model.mappers.MovieMapper;
import com.diakogiannis.uel.masters.moviebook.service.MovieService;
import com.diakogiannis.uel.masters.moviebook.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    MovieService movieService;

    @Autowired
    RatingService ratingService;

    @GetMapping(value = {"/list/{publicIdentifier}", "/list"})
    public String getHome(
            @RequestParam(name = "sortBy", required = false) Integer sortBy,
            Model model, Principal principal,
            @PathVariable(required = false, name = "publicIdentifier") String publicIdentifier) {
        log.debug("Entering Movies Home...");
        Iterable<Movie> movies = movieService.getMovies(SortByEnum.valueOfLabel(sortBy), publicIdentifier);
        Map<Long, MovieRatingDTO> userMovieRatings = new HashMap<>();
        if (principal != null && principal.getName() != null) {
            //Add Ratings
            userMovieRatings = movieService.getUserMovieRatings(principal.getName());
        }

        if (publicIdentifier != null) {
            model.addAttribute("isPublicIdentifier", true);
        } else {
            model.addAttribute("isPublicIdentifier", false);
        }

        model.addAttribute("movies", movies);
        model.addAttribute("userMovieRatings", userMovieRatings);

        return UrlBindingsEnum.MOVIES_HOME_TEMPLATE.getValue();
    }


    @GetMapping(path = "/create")
    public String getCreate(Model model) {
        log.debug("Entering Create Movie...");
        model.addAttribute("movie", new MovieDTO());
        return UrlBindingsEnum.MOVIES_CREATE_TEMPLATE.getValue();
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public String createMovie(Model model, @Valid MovieDTO movie, BindingResult result, Principal principal) {
        log.debug("Creating Movie...");
        if (result.hasErrors()) {
            return UrlBindingsEnum.MOVIES_CREATE_TEMPLATE.getValue();
        }
        movieService.saveNewMovie(movieMapper.toMovie(movie), principal.getName());
        model.addAttribute("created", true);
        return UrlBindingsEnum.MOVIES_CREATE_TEMPLATE.getValue();
    }

    @Secured("ROLE_USER")
    @GetMapping(value = {"/rate/{action}/{movieId}"})
    public String rateMovie(@PathVariable(required = true, name = "action") String action,
                            @PathVariable(required = true, name = "movieId") Long movieId,
                            Principal principal) {
        ratingService.castVote(LikeEnum.valueOfLabel(action), principal.getName(), movieService.findMovieWithUserDetails(movieId));
        return "redirect:/" + UrlBindingsEnum.MOVIES_HOME_URI.getValue();
    }

}
