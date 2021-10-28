package com.example.moviesapp.ui.Movies

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.data.local.MoviesFav
import com.example.moviesapp.databinding.FragmentMoviesSearchBinding
import com.example.moviesapp.ui.Favorites.DaoViewModel
import com.example.moviesapp.ui.MovieApiStatus
import com.example.moviesapp.ui.MoviesListViewModel
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesSearch : Fragment(R.layout.fragment_movies_search),
    MoviesListAdapter.OnItemClickListener {
    private val args: MoviesSearchArgs by navArgs()
    private val daoViewModel by viewModels<DaoViewModel>()
    private val viewModel by viewModels<MoviesListViewModel>()
    private var _binding: FragmentMoviesSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesSearchBinding.bind(view)



        viewModel.searchMovies(args.searchTerm)
        val adapter = MoviesListAdapter(this, daoViewModel)
        //Observe movies
        viewModel.moviesSearchResults.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        //Based on network state, display list of movies
        viewModel.networkState.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = if (it == MovieApiStatus.LOADING) true else view.isGone
            binding.errorTextView.isVisible = if (it == MovieApiStatus.ERROR) true else view.isGone
            binding.recyclerView.isVisible = if (it == MovieApiStatus.DONE) true else view.isGone
        })
        //Combine the two livedata to set conditions for the no results text
        combineTuple(viewModel.moviesSearchResults, viewModel.networkState).observe(
            viewLifecycleOwner
        ) {
            binding.noResultsText.isVisible =
                it == Pair<List<MoviesResults.Movies>, MovieApiStatus>(
                    listOf(),
                    MovieApiStatus.DONE
                )
        }
        //Observe the list of ids
        daoViewModel.idList.observe(viewLifecycleOwner) {
        }


        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            //Disable animations
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }



        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_gallery, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView




        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //Search functionality based on query
                    viewModel.searchMovies(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemClick(movie: MoviesResults.Movies) {
        val action = MoviesSearchDirections.actionMoviesSearchToMoviesDetailsFragment(
            movie
        )
        findNavController().navigate(action)
    }

    override fun onFavoriteClick(fav: MoviesFav) {
        daoViewModel.addMovieToFavs(fav)
    }

    override fun onDeleteClick(fav: MoviesFav) {
        daoViewModel.deleteMovieFromFavs(fav)
    }
}





