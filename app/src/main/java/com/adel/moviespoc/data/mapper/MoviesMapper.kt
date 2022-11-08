package com.adel.moviespoc.data.mapper

import com.adel.moviespoc.data.models.RemoteMovie
import com.adel.moviespoc.domain.entities.Movie

fun RemoteMovie.toMovie(): Movie? {
    return if (id == null || title == null) null
    else Movie(id, title)

}


// TODO add mapper for movieDetails