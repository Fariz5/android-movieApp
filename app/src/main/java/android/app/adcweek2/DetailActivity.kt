package android.app.adcweek2

import android.app.adcweek2.AppConstant.BASE_URL_IMAGE
import android.app.adcweek2.adapter.StudiosAdapter
import android.app.adcweek2.utils.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share

class DetailActivity : AppCompatActivity(), StudiosAdapter.OnStudiosItemListener {

    companion object {
        fun start(context: Context, movie: Movie) {
            Intent(context, DetailActivity::class.java).apply {
                this.putExtra(BundleKeys.MOVIE, movie)
                context.startActivity(this)
            }
        }
    }

    private var movie: Movie? = null

    private val studios: MutableList<Studios> = mutableListOf()

    private lateinit var studiosAdapter: StudiosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        movie = intent.getParcelableExtra(BundleKeys.MOVIE)

        setSupportActionBar(toolbarDetail)
        supportActionBar?.title = emptyString()

        window?.setFull()

        studiosAdapter = StudiosAdapter(this, mutableListOf(), this)

        rvCast.apply {
            layoutManager = LinearLayoutManager(
                this@DetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = studiosAdapter
        }

        movie?.let { movie ->
            imgBanner.setImageUrl(this, BASE_URL_IMAGE + movie.banner, pbBanner)
            tvMovieTitle.text = movie.title
            tvSubTitle.text = String.format(
                getString(R.string.format_sub_title),
                movie.releaseDate?.toViewDate(),
                movie.genre.joinToString(),
                movie.language
            )
            imgPoster.setImageUrl(this, BASE_URL_IMAGE + movie.poster, pbPoster)
            tvOverview.text = movie.description
            tvMovieRating.text = String.format(getString(R.string.format_rating), movie.rating)

            studios.addAll(movie.studios.orEmpty())

            studiosAdapter.setData(studios)

            movie.trailerUrl?.let { url ->
                btnPlay.visible()
                btnPlay.setOnClickListener {
                    btnPlay.gone()
                    supportActionBar?.title = movie.title
                    youtube_container.visible()
                    lifecycle.addObserver(youtube_player_view)
                    youtube_player_view.addYouTubePlayerListener(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                val videoUri = url.toUri()
                                val videoId = videoUri.getQueryParameter("v")
                                youTubePlayer.loadVideo(videoId.toString(), 0f)
                                youTubePlayer.setVolume(100)
                                showDetail(movie)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun showDetail(movie: Movie) {
        container_detail.visible()
        tvDetailTitle.text = movie.title
        tvDetailGenre.text = movie.genre.joinToString()
        tvDetailDateAndLanguage.text = String.format(
            getString(R.string.format_date_and_language),
            movie.releaseDate?.toViewDate(),
            movie.language
        )

        btnDownload.setOnClickListener {
            showToast("Download not Available")
        }

        btnShare.setOnClickListener {
            share(String.format(getString(R.string.format_share), movie.title, movie.trailerUrl), movie.trailerUrl.toString())
        }

        btnOpen.setOnClickListener {
            browse(movie.trailerUrl.toString())
        }
    }

    override fun onStudiosItemClicked(studios: Studios) {
        showToast(studios.name.toString())
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