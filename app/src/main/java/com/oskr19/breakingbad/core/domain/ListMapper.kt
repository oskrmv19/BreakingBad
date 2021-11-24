package com.oskr19.breakingbad.core.domain

interface ListMapper<I, O>: Mapper<I, O> {
    fun mapFromList(type: List<I>?): List<O> {
        type?.let {
            return type.map {
                mapFrom(it)
            }
        }
        return listOf()
    }
    fun mapToList(type: List<O>?): List<I> {
        type?.let {
            return type.map {
                mapTo(it)
            }
        }
        return listOf()
    }
}