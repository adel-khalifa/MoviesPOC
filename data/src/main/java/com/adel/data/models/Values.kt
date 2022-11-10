package com.adel.data.models

@JvmInline
value class CurrentPage(val value: Int)

@JvmInline
value class TotalPages(val value: Int)


@JvmInline
value class MovieId(val value: Int)


data class PaginatedResponse<T>(
    val page: CurrentPage,
    val totalPages: TotalPages,
    val list: List<T>
)
