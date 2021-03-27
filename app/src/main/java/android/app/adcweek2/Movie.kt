package android.app.adcweek2

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("poster")
    val poster : String?,
    @SerializedName("banner")
    val banner : String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("genre")
    val genre : List<String?>,
    @SerializedName("language")
    val language : String?,
    @SerializedName("cast")
    val studios : List<Studios>?,
    @SerializedName("trailer_url")
    val trailerUrl : String?,
    @SerializedName("vote_average")
    val rating: Double
) : Parcelable