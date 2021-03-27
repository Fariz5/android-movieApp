package android.app.adcweek2.adapter

import android.app.adcweek2.AppConstant
import android.app.adcweek2.Movie
import android.app.adcweek2.R
import android.app.adcweek2.utils.setImageUrl
import android.app.adcweek2.utils.toViewDate
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(
    private val context: Context,
    private val data: MutableList<Movie> = mutableListOf(),
    val listener: OnMovieItemListener? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Movie) {
            with(itemView) {
                imgPosterMovie.setImageUrl(context, AppConstant.BASE_URL_IMAGE + data.poster, pb_image)
                tv_title_item.text = data.title
                tv_description_item.text = data.description
                tv_release_date.text = data.releaseDate?.toViewDate()

                setOnClickListener {
                    listener?.onMovieItemClicked(data)
                }
            }
        }
    }

    fun setData(list: List<Movie>) {
        if (data.size > 0) {
            data.clear()
        }
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun addOrUpdate(item: Movie) {
        val i: Int = data.indexOf(item)
        if (i >= 0) {
            data[i] = item
            notifyDataSetChanged()
        } else {
            data.add(item)
            notifyDataSetChanged()
        }
    }

    interface OnMovieItemListener {
        fun onMovieItemClicked(movie: Movie)
    }
}