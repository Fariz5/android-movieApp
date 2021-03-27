package android.app.adcweek2

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Studios(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("logo_path")
    val poster : String?,
    @SerializedName("origin_country")
    val origin : String?
) : Parcelable