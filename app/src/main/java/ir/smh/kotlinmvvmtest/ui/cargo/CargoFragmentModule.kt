package ir.smh.kotlinmvvmtest.ui.cargo

import dagger.Module
import dagger.Provides
import ir.smh.kotlinmvvmtest.data.repository.cargo.CargoRepository
import ir.smh.kotlinmvvmtest.data.repository.cargo.CargoRepositoryFactory
import ir.smh.kotlinmvvmtest.data.repository.cargo.CargoRepositoryImpl

/**
 * Created by m.hejazi on 5/14/18.
 */
@Module
class CargoFragmentModule {
    @Provides
    fun provideUserRepository(cargoRepositoryFactory: CargoRepositoryFactory): CargoRepository {
        return CargoRepositoryImpl(cargoRepositoryFactory)
    }
}
