package ir.smh.kotlinmvvmtest.data.repository.cargo

import io.reactivex.Single
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel

interface CargoRepository {
    fun getCargoItems(page: Int?): Single<List<CargoModel>>
}
