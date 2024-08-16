package com.practice.openinapp.model_class

import java.io.Serializable

data class Link(
    val linkName: String,
    val linkDate: String,
    val linkUrl: String,
    val clickCount: Int
): Serializable
