package com.oskr19.breakingbad.modules.characters

import com.oskr19.breakingbad.core.data.LocalDatabase
import com.oskr19.breakingbad.modules.characters.data.CharacterDao
import com.oskr19.breakingbad.modules.characters.data.CharacterMapper
import com.oskr19.breakingbad.modules.characters.data.CharactersAPI
import com.oskr19.breakingbad.modules.characters.data.FetchCharactersRepositoryImpl
import com.oskr19.breakingbad.modules.characters.data.LocalCharactersDatasource
import com.oskr19.breakingbad.modules.characters.data.LocalCharactersDatasourceImpl
import com.oskr19.breakingbad.modules.characters.data.RemoteCharactersDatasource
import com.oskr19.breakingbad.modules.characters.data.RemoteCharactersDatasourceImpl
import com.oskr19.breakingbad.modules.characters.domain.FetchCharactersRepository
import com.oskr19.breakingbad.modules.characters.domain.FetchLocalCharactersUseCase
import com.oskr19.breakingbad.modules.characters.domain.FetchRemoteCharactersUseCase
import com.oskr19.breakingbad.modules.characters.presentation.CharacterAdapter
import com.oskr19.breakingbad.modules.favorites.data.FavoriteDao
import com.oskr19.breakingbad.modules.favorites.data.FavoriteRepository
import com.oskr19.breakingbad.modules.favorites.data.FavoriteRepositoryImpl
import com.oskr19.breakingbad.modules.favorites.data.LocalFavoriteDatasource
import com.oskr19.breakingbad.modules.favorites.data.LocalFavoriteDatasourceImpl
import com.oskr19.breakingbad.modules.favorites.data.SetFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class DataModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAPI(retrofit: Retrofit): CharactersAPI {
        return retrofit.create(CharactersAPI::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDao(localDatabase: LocalDatabase): CharacterDao {
        return localDatabase.characterDao()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideFavoriteDao(localDatabase: LocalDatabase): FavoriteDao {
        return localDatabase.favoriteDao()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideMapper(): CharacterMapper {
        return CharacterMapper()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(
        characterMapper: CharacterMapper,
        remoteCharactersDatasource: RemoteCharactersDatasource,
        localCharactersDatasource: LocalCharactersDatasource,
        localFavoriteDatasource: LocalFavoriteDatasource
    ): FetchCharactersRepository {
        return FetchCharactersRepositoryImpl(
            characterMapper,
            localCharactersDatasource,
            remoteCharactersDatasource,
            localFavoriteDatasource
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideFavoriteRepository(localFavoriteDatasource: LocalFavoriteDatasource): FavoriteRepository {
        return FavoriteRepositoryImpl(localFavoriteDatasource)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLocalDatasource(characterDao: CharacterDao): LocalCharactersDatasource = LocalCharactersDatasourceImpl(characterDao)

    @Provides
    @ActivityRetainedScoped
    fun provideRemoteDatasource(charactersAPI: CharactersAPI): RemoteCharactersDatasource = RemoteCharactersDatasourceImpl(charactersAPI)

    @Provides

    @ActivityRetainedScoped
    fun provideLocalFavoriteDatasource(favoriteDao: FavoriteDao): LocalFavoriteDatasource = LocalFavoriteDatasourceImpl(favoriteDao)
}

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideFetchRemoteCharactersUseCase(fetchCharactersRepository: FetchCharactersRepository): FetchRemoteCharactersUseCase {
        return FetchRemoteCharactersUseCase(fetchCharactersRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideFetchLocalCharactersUseCase(fetchCharactersRepository: FetchCharactersRepository): FetchLocalCharactersUseCase {
        return FetchLocalCharactersUseCase(fetchCharactersRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSetFavoriteUseCase(favoriteRepository: FavoriteRepository): SetFavoriteUseCase {
        return SetFavoriteUseCase(favoriteRepository)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(): CharacterAdapter {
        return CharacterAdapter()
    }
}