package com.practice.openinapp.model_class

import java.io.Serializable

data class Stats(
    val imageResourceId: Int, val statName: String, val statValue: String): Serializable
