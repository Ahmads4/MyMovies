package com.example.moviesapp.ui

import androidx.lifecycle.*
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_QUERY = " "

//Enum class for network state
enum class MovieApiStatus { LOADING, ERROR, DONE }


@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MoviesRepository,
) : ViewModel() {


    private var currentQuery = MutableLiveData(DEFAULT_QUERY)

    private val _moviesAction = MutableLiveData<List<MoviesResults.Movies>>()
    val moviesAction: LiveData<List<MoviesResults.Movies>> = _moviesAction

    private val _moviesComedy = MutableLiveData<List<MoviesResults.Movies>>()
    val moviesComedy: LiveData<List<MoviesResults.Movies>> = _moviesComedy

    private val _moviesHorror = MutableLiveData<List<MoviesResults.Movies>>()
    val moviesHorror: LiveData<List<MoviesResults.Movies>> = _moviesHorror

    private val _moviesRomance = MutableLiveData<List<MoviesResults.Movies>>()
    val moviesRomance: LiveData<List<MoviesResults.Movies>> = _moviesRomance

    private val _moviesScifi = MutableLiveData<List<MoviesResults.Movies>>()
    val moviesScifi: LiveData<List<MoviesResults.Movies>> = _moviesScifi

    private val _moviesTrending = MutableLiveData<List<MoviesResults.Movies>>()
    val moviesTrending: LiveData<List<MoviesResults.Movies>> = _moviesTrending

    private val _networkState = MutableLiveData<MovieApiStatus>()
    val networkState: LiveData<MovieApiStatus> = _networkState

    init {
        getMovies()
    }

    //Get list of movies using switchmap based on user query
    var movies = currentQuery.switchMap { queryString ->
        liveData {
            emit(repository.getSearchResults(queryString))
        }
    }

    //Mediator Live Data to emit two values for network success and failure
    var moviesSearchResults = MediatorLiveData<List<MoviesResults.Movies>>()


    fun searchMovies(query: String) {

        currentQuery.value = query

    }

    private fun getMovies() {
        viewModelScope.launch {
            _networkState.value = MovieApiStatus.LOADING
            try {
                _networkState.value = MovieApiStatus.DONE
                _moviesAction.value = repository.getActionMovies()
                _moviesComedy.value = repository.getComedyMovies()
                _moviesHorror.value = repository.getHorrorMovies()
                _moviesRomance.value = repository.getRomanceMovies()
                _moviesScifi.value = repository.getScifiMovies()
                _moviesTrending.value = repository.getTrendingMovies()
                moviesSearchResults.addSource(movies) {
                    moviesSearchResults.value = it

                }

            } catch (e: Exception) {
                _networkState.value = MovieApiStatus.ERROR
                _moviesAction.value = listOf()
                _moviesComedy.value = listOf()
                _moviesHorror.value = listOf()
                _moviesScifi.value = listOf()
                _moviesRomance.value = listOf()
                _moviesTrending.value = listOf()
                moviesSearchResults.value = listOf()



            }

        }
    }


    class MoviesListViewModelFactory @Inject constructor(private val repository: MoviesRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MoviesListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }


    }


}


