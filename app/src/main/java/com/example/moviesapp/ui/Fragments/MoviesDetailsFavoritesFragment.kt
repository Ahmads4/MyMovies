package com.example.moviesapp.ui.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMoviesDetailsFavoritesBinding
import com.example.moviesapp.data.local.MoviesFav
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.ui.Adapters.IMAGE_BASE_URL
import com.example.moviesapp.ui.ViewModels.DaoViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailsFavoritesFragment : Fragment(R.layout.fragment_movies_details_favorites) {


    private val args by navArgs<MoviesDetailsFavoritesFragmentArgs>()
    private val daoViewModel by viewModels<DaoViewModel>()
    private fun showToast(string: String) {
        Toast.makeText(view?.context, string, Toast.LENGTH_SHORT).show()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMoviesDetailsFavoritesBinding.bind(view)





        binding.apply {
            val movie: MoviesResults.Movies = args.movie
            val fav = MoviesFav(
                movie.title,
                movie.id,
                movie.release_date,
                movie.overview,
                movie.vote_average,
                movie.poster_path,
                movie.original_language,
                movie.isFavorite,
            )


            //When you are in fragment/activity, pass it to a glide.with because view is less efficient
            Glide.with(this@MoviesDetailsFavoritesFragment)
                .load(IMAGE_BASE_URL + movie.poster_path)
                //Have the textview visible only when image is visible
                .error(R.drawable.ic_baseline_error_outline_24)
                .fitCenter()
                .into(coverPhoto)

            title.text = movie.title
            releaseDate.text = movie.release_date
            language.text = movie.original_language
            rating.text = movie.vote_average
            plot.text = movie.overview
           favCheckbox.isChecked = true








            favCheckbox.setOnClickListener {


               if(!favCheckbox.isChecked)  {

                    fav.isFavorite = false
                    daoViewModel.deleteMovieFromFavs(fav)
                    showToast("${fav.title} is removed from your favorites")


                }


            }
        }


    }


}