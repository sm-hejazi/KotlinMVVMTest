package ir.smh.kotlinmvvmtest.data.repository.cargo

import io.reactivex.Single
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.data.model.cargo.Paging

class CargoRepositoryImpl(private val repositoryFactory: CargoRepositoryFactory) : CargoRepository {


    override fun getCargoItems(page: Int?): Single<List<CargoModel>> {
        return repositoryFactory.getCargoItems(Paging(page))
    }
}
