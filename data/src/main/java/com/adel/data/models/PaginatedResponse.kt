package com.adel.data.models

import com.adel.models.values.CurrentPage
import com.adel.models.values.TotalPages


data class PaginatedResponse<T>(
    val page: CurrentPage,
    val totalPages: TotalPages,
    val list: List<T>
)
