package com.oskr19.breakingbad.modules.characters.domain

object FetchCharactersRequest {
    fun buildFetchRequest(offset: Int, limit: Int) = hashMapOf(
        "limit" to limit,
        "offset" to offset
    )
}