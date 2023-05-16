package com.example.imdb_api.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb_api.R
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.presentation.movies.MoviesSearchViewModel
import com.example.imdb_api.ui.models.MoviesState
import com.example.imdb_api.ui.poster.PosterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val viewModel by viewModel<MoviesSearchViewModel>()

    private var textWatcher: TextWatcher? = null

    private val adapter = MoviesAdapter(
        object : MoviesAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                if (clickDebounce()) {
                    val intent = Intent(this@MoviesActivity, PosterActivity::class.java)
                    intent.putExtra("poster", movie.image)
                    startActivity(intent)
                }
            }

            override fun onFavoriteToggleClick(movie: Movie) {
                viewModel.toggleFavorite(movie)
            }

        }
    )

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewModel.observeState().observe(this) { moviesState ->
            render(moviesState)
        }
        viewModel.observeShowToast().observe(this) { message ->
                showToastMessage(message)
            }



        queryInput = findViewById(R.id.queryInput)
        placeholderMessage = findViewById(R.id.placeholderMessage)
        moviesList = findViewById(R.id.locations)
        progressBar = findViewById(R.id.progressBar)

        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        textWatcher.let { queryInput.addTextChangedListener(it) }

    }

    override fun onDestroy() {
        super.onDestroy()
        //удаляем listener
        textWatcher?.let { queryInput.removeTextChangedListener(it) }
    }

    private fun showLoading() {
        placeholderMessage.visibility = View.GONE
        moviesList.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        placeholderMessage.visibility = View.VISIBLE
        moviesList.visibility = View.GONE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    private fun showContent(movies: List<Movie>) {
        placeholderMessage.visibility = View.GONE
        moviesList.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)

        adapter.notifyDataSetChanged()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading ->
                showLoading()
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Content -> {
                showContent(state.listFilms)
            }
        }
    }
    private fun showToastMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}