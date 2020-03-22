package com.example.brubankmovies.network

/**
 * Contains a list of [Movie].
 */
class MovieContainer(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<Movie>) {
}