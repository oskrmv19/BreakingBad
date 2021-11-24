package com.oskr19.breakingbad.modules.characters.presentation

interface CharacterItemListener {
    fun onItemClick(position: Int)
    fun setFavorite(position: Int, isFavorite: Boolean)
}
