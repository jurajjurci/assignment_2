@file:OptIn(ExperimentalPagingApi::class)

package sk.jurci.core_repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.jurci.core_database.model.MovieEntity
import sk.jurci.core_database.repository.IDatabaseRepository
import sk.jurci.core_network.repository.INetworkRepository
import sk.jurci.core_repository.repository.IMovieEntityRepository
import sk.jurci.core_repository.repository.MovieEntityRepository
import sk.jurci.core_repository.mediator.MovieListRemoteMediator
import sk.jurci.core_repository.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    internal fun providesMovieEntityRepository(
        pager: Pager<Int, MovieEntity>,
        movieListRemoteMediator: MovieListRemoteMediator,
    ): IMovieEntityRepository = MovieEntityRepository(
        pager = pager,
        movieListRemoteMediator = movieListRemoteMediator
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