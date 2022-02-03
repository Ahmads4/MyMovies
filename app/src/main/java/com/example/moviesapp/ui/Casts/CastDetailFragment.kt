package com.example.moviesapp.ui.Casts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.remote.Casts.Casts
import com.example.moviesapp.databinding.FragmentCastDetailBinding
import com.example.moviesapp.ui.Movies.IMAGE_BASE_URL
import com.example.moviesapp.ui.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastDetailFragment : Fragment(R.layout.fragment_cast_detail) {
    private val args by navArgs<CastDetailFragmentArgs>()
    private val viewModel by viewModels<MoviesListViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCastDetailBinding.bind(view)
        binding.apply {
            val cast: Casts.Crew = args.cast
            Glide.with(this@CastDetailFragment)
                .load(IMAGE_BASE_URL + cast.profile_path)
                .error(R.drawable.ic_baseline_error_outline_24)
                .fitCenter()
                .into(castImage)
            castName.text = cast.name
            viewModel.getCastDetails(cast.id)
            viewModel.castDetails.observe(viewLifecycleOwner) {
                biographyText.text = it.biography
            }
        }
    }
}