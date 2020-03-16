package com.example.brubankmovies.network

class MovieContainer(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<Movie>) {
}