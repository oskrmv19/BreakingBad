package com.oskr19.breakingbad.modules.characters.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oskr19.breakingbad.core.presentation.BaseViewModel
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import com.oskr19.breakingbad.modules.characters.domain.FetchLocalCharactersUseCase
import com.oskr19.breakingbad.modules.characters.domain.FetchRemoteCharactersUseCase
import com.oskr19.breakingbad.modules.favorites.data.SetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class CharactersViewModel @Inject constructor(
    application: Application,
    private val fetchRemoteCharactersUseCase: FetchRemoteCharactersUseCase,
    private val fetchLocalCharactersUseCase: FetchLocalCharactersUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase
) : BaseViewModel(application) {

    private var characterList = mutableListOf<CharacterDTO>()

    private val _characters = MutableLiveData<List<CharacterDTO>>()
    val characters: LiveData<List<CharacterDTO>> get() = _characters

    fun fetchFromRemote(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRemoteCharactersUseCase.run(FetchRemoteCharactersUseCase.Params(offset))
                .onStart { postEventLoading() }
                .catch {
                    handleFailure(it)
                }
                .collect {
                    postEventFinished()
                    collectCharacters(it)
                }
        }
    }

    fun setFavorite(characterId: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setFavoriteUseCase.run(SetFavoriteUseCase.Params(characterId, isFavorite)).collect()
        }
    }

    private fun collectCharacters(list: List<CharacterDTO>) {
        characterList.addAll(0, list)
        _characters.postValue(characterList)
    }

    private fun fetchLocalCharacterList() {
        characterList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            fetchLocalCharactersUseCase.run(FetchLocalCharactersUseCase.Params(""))
                .catch { handleFailure(it) }
                .collect { list ->
                    collectCharacters(list.sortedWith(compareByDescending<CharacterDTO> { it.isFavorite }.thenByDescending { it.createdAt }))
                    if (list.isEmpty()) {
                        fetchFromRemote(list.size)
                    }
                }
        }
    }

    fun fetchFromLocal() {
        fetchLocalCharacterList()
    }
}