@file:OptIn(ExperimentalPagingApi::class)

package sk.jurci.core_repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sk.jurci.core_database.AppDatabase
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_network.ApiService
import sk.jurci.core_repository.repository.IMovieEntityRepository
import sk.jurci.core_repository.repository.MovieEntityRepository
import sk.jurci.core_repository.mediator.MovieListRemoteMediator
import sk.jurci.core_repository.util.Constants
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideMovieEntityRepository(
        pager: Pager<Int, MovieEntity>,
    ): IMovieEntityRepository = MovieEntityRepository(pager = pager)

    @Provides
    @Singleton
    fun provideMoviePager(
        appDatabase: AppDatabase,
        apiService: ApiService,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MOVIE_PAGE_SIZE),
            remoteMediator = MovieListRemoteMediator(
                appDatabase = appDatabase,
                apiService = apiService,
            ),
            pagingSourceFactory = {
                appDatabase.movieDao.pagingSource()
            },
        )
    }
}