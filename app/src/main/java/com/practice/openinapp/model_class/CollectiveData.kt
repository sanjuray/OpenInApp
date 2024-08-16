package com.practice.openinapp.model_class

import com.google.gson.annotations.SerializedName

data class CollectiveData(
    @SerializedName("favourite_links")
    val favouriteLinks: List<Any>,
    @SerializedName("overall_url_chart")
    val overallUrlChart: Map<String, Double>,
    @SerializedName("recent_links")
    val recentLinks: List<LinkOfAPI>,
    @SerializedName("top_links")
    val topLinks: List<LinkOfAPI>
)
