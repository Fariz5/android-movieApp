package android.app.adcweek2.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ImageView.setImageUrl(context: Context, imageUrl: String, progressBar: ProgressBar) {
    if (!isValidContext(context)) return

    progressBar.visible()
    Glide.with(context)
        .load(imageUrl)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean,
            ): Boolean {
                progressBar.gone()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }
        }).into(this)
}

fun ImageView.setImageUrl(
    c: Context,
    imageUrl: String,
    progressBar: ProgressBar,
    errorResourceId: Int
) {
    if (!isValidContext(c)) return

    val options = RequestOptions()
        .error(errorResourceId)

    progressBar.visible()
    Glide.with(c)
        .load(imageUrl)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }
        })
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(options)
        .into(this)
}

fun ImageView.setImageBase64(
    context: Context,
    base64Image: String,
    progressBar: ProgressBar,
    errorResourceId: Int,
    isCenterCrop: Boolean = false
) {

    if (!isValidContext(context)) return

    val options = RequestOptions()
        .error(errorResourceId)

    progressBar.visible()

    val imageByteArray = Base64.decode(base64Image, Base64.DEFAULT)

    if (isCenterCrop) options.centerCrop()

    Glide.with(context)
        .load(imageByteArray)
        .apply(options)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }
        }).into(this)
}

fun isValidContext(context: Context): Boolean {
    val activity = context as? Activity
    return if (activity != null) {
        !(activity.isDestroyed || activity.isFinishing)
    } else {
        true
    }
}