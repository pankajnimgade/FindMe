package com.findme.businesslogic.model

data class SearchResponse(
    val description: String,
    val generator: String,
    val items: List<Item>,
    val link: String,
    val modified: String,
    val title: String
)