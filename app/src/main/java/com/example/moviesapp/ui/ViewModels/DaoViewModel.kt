package com.example.moviesapp.ui.ViewModels

import androidx.lifecycle.*
import com.example.moviesapp.data.local.MoviesFav
import com.example.moviesapp.data.local.MoviesFavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class DaoViewModel @Inject constructor(
    private val repository: MoviesFavoritesRepository
) :
    ViewModel() {


   private val _checkBoxValue = MutableLiveData<Boolean>()
     val checkBoxValue: LiveData<Boolean> = _checkBoxValue


    val favMovies: LiveData<List<MoviesFav>> = repository.favoriteMovies.asLiveData()

    val idList = repository.favIdList.asLiveData()







   fun getValue(id: Int) {
       viewModelScope.launch {
           _checkBoxValue.value = repository.getBool(id)

       }

    }



    fun addMovieToFavs(favorite: MoviesFav) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)

        }
    }

    fun deleteMovieFromFavs(favorite: MoviesFav) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }

    }




    class DaoViewModelFactory @Inject constructor(
        private val repository: MoviesFavoritesRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DaoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DaoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }


    }


}




