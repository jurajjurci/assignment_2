package sk.jurci.core_repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sk.jurci.core_network.ApiService
import sk.jurci.core_repository.repository.INetworkRepository
import sk.jurci.core_repository.repository.NetworkRepository
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
    fun provideNetworkRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        apiService: ApiService,
    ): INetworkRepository = NetworkRepository(
        ioDispatcher = ioDispatcher,
        apiService = apiService,
    )
}