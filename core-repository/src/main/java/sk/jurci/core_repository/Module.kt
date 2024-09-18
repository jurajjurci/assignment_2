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
import sk.jurci.core_database.database.AppDatabase
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_network.service.ApiService
import sk.jurci.core_repository.repository.IDatabaseRepository
import sk.jurci.core_repository.repository.INetworkRepository
import sk.jurci.core_repository.repository.IMovieDomainRepository
import sk.jurci.core_repository.repository.MovieDomainRepository
import sk.jurci.core_repository.mediator.MovieListRemoteMediator
import sk.jurci.core_repository.repository.DatabaseRepository
import sk.jurci.core_repository.repository.NetworkRepository
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
    internal fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Singleton
    internal fun providesDatabaseRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        appDatabase: AppDatabase,
    ): IDatabaseRepository = DatabaseRepository(
        ioDispatcher = ioDispatcher,
        appDatabase = appDatabase,
    )

    @Provides
    @Singleton
    internal fun providesNetworkRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        apiService: ApiService,
    ): INetworkRepository = NetworkRepository(
        ioDispatcher = ioDispatcher,
        apiService = apiService,
    )

    @Provides
    @Singleton
    internal fun providesMovieEntityRepository(
        pager: Pager<Int, MovieEntity>,
        movieListRemoteMediator: MovieListRemoteMediator,
        databaseRepository: IDatabaseRepository,
    ): IMovieDomainRepository = MovieDomainRepository(
        pager = pager,
        movieListRemoteMediator = movieListRemoteMediator,
        databaseRepository = databaseRepository,
    )

    @Provides
    @Singleton
    internal fun providesMovieListRemoteMediator(
        databaseRepository: IDatabaseRepository,
        networkRepository: INetworkRepository
    ): MovieListRemoteMediator = MovieListRemoteMediator(
        databaseRepository = databaseRepository,
        networkRepository = networkRepository,
    )

    @Provides
    @Singleton
    internal fun providesMoviePager(
        movieListRemoteMediator: MovieListRemoteMediator,
        databaseRepository: IDatabaseRepository,
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MOVIE_PAGE_SIZE),
            remoteMediator = movieListRemoteMediator,
            pagingSourceFactory = {
                databaseRepository.moviePagingSource()
            },
        )
    }
}