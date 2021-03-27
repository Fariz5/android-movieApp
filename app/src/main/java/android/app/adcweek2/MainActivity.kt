package android.app.adcweek2

import android.app.adcweek2.adapter.MovieAdapter
import android.app.adcweek2.utils.getResultDataFromAsset
import android.app.adcweek2.utils.showToast
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieAdapter.OnMovieItemListener {

    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private val movieList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieAdapter = MovieAdapter(this, mutableListOf(), this)

        setSupportActionBar(toolbarMain)

        rvMain.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        movieList.addAll(getResultDataFromAsset(this, "movie_response.json")?.result.orEmpty())

        movieAdapter.setData(movieList)

        imgUser.setOnClickListener {
            showToast("Coming Soon")
        }
    }

    override fun onMovieItemClicked(movie: Movie) {
        DetailActivity.start(this, movie)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }
}