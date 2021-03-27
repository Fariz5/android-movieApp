package android.app.adcweek2.adapter

import android.app.adcweek2.AppConstant.BASE_URL_IMAGE
import android.app.adcweek2.Studios
import android.app.adcweek2.R
import android.app.adcweek2.utils.setImageUrl
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cast.view.*

class StudiosAdapter(
    private val context: Context,
    private val data: MutableList<Studios> = mutableListOf(),
    val listener: OnStudiosItemListener? = null
) : RecyclerView.Adapter<StudiosAdapter.StudiosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudiosViewHolder {
        return StudiosViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: StudiosViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class StudiosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Studios) {
            with(itemView) {
                imgPoster.setImageUrl(context, BASE_URL_IMAGE + data.poster, pbPoster)
                tvName.text = data.name
                tvOrigin.text = data.origin

                setOnClickListener {
                    listener?.onStudiosItemClicked(data)
                }
            }
        }
    }

    fun setData(list: List<Studios>) {
        if (data.size > 0) {
            data.clear()
        }
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun addOrUpdate(item: Studios) {
        val i: Int = data.indexOf(item)
        if (i >= 0) {
            data[i] = item
            notifyDataSetChanged()
        } else {
            data.add(item)
            notifyDataSetChanged()
        }
    }

    interface OnStudiosItemListener {
        fun onStudiosItemClicked(studios: Studios)
    }
}