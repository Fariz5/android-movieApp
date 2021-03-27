package android.app.adcweek2

import com.google.gson.annotations.SerializedName

data class ResultData(
    @SerializedName("results")
    val result : List<Movie>
)
