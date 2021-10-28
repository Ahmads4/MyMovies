package com.example.moviesapp.ui.Movies

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMoviesDiscoverBinding
import com.example.moviesapp.ui.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDiscoverFragment : Fragment(R.layout.fragment_movies_discover) {
    private val viewModel by viewModels<MoviesListViewModel>()
    private var _binding: FragmentMoviesDiscoverBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesDiscoverBinding.bind(view)

        binding.actionImage.setOnClickListener {
            val action =
                MoviesDiscoverFragmentDirections.actionMoviesDiscoverFragmentToActionMoviesFragment()
            findNavController().navigate(action)
        }

        binding.comedyImage.setOnClickListener {
            val action =
                MoviesDiscoverFragmentDirections.actionMoviesDiscoverFragmentToComedyMoviesFragment()
            findNavController().navigate(action)
        }

        binding.horrorImage.setOnClickListener {
            val action =
                MoviesDiscoverFragmentDirections.actionMoviesDiscoverFragmentToHorrorMoviesFragment()
            findNavController().navigate(action)
        }

        binding.romanceImage.setOnClickListener {
            val action =
                MoviesDiscoverFragmentDirections.actionMoviesDiscoverFragmentToRomanceMoviesFragment()
            findNavController().navigate(action)
        }

        binding.scifiImage.setOnClickListener {
            val action =
                MoviesDiscoverFragmentDirections.actionMoviesDiscoverFragmentToScifiMoviesFragment()
            findNavController().navigate(action)
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
                    viewModel.searchMovies(query)
                    searchView.clearFocus()
                    val action =
                        MoviesDiscoverFragmentDirections.actionMoviesDiscoverFragmentToMoviesSearch(
                            query
                        )
                    findNavController().navigate(action)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}




