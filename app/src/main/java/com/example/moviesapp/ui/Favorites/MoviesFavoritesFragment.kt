package com.example.moviesapp.ui.Favorites

import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.data.local.MoviesFav
import com.example.moviesapp.databinding.FragmentMoviesFavoritesBinding
import com.example.moviesapp.ui.MovieApiStatus
import com.example.moviesapp.ui.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFavoritesFragment : Fragment(R.layout.fragment_movies_favorites),
    MoviesFavoritesAdapter.OnItemClickListener {
    private val viewModel by viewModels<MoviesListViewModel>()
    private val daoViewModel by viewModels<DaoViewModel>()
    private var _binding: FragmentMoviesFavoritesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesFavoritesBinding.bind(view)
        val adapter = MoviesFavoritesAdapter(this)
        //Observe movies
        daoViewModel.favMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        //Observe network state
        viewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = if (it == MovieApiStatus.LOADING) true else view.isGone
            binding.errorTextView.isVisible = if (it == MovieApiStatus.ERROR) true else view.isGone
            binding.recyclerView.isVisible = if (it == MovieApiStatus.DONE) true else view.isGone
        }

        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            //Disable animations
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflate the gallery menu
        inflater.inflate(R.menu.menu_gallery, menu)
    }

    override fun onItemClick(movie: MoviesResults.Movies) {
        val action =
           MoviesFavoritesFragmentDirections.actionMoviesFavoritesToMoviesDetailsFavoritesFragment22(movie)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(fav: MoviesFav) {
        daoViewModel.deleteMovieFromFavs(fav)
    }
}