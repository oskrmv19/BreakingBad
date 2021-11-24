package com.oskr19.breakingbad.core.domain

interface Mapper<I, O> {
    fun mapFrom(type: I): O
    fun mapTo(type: O): I
}