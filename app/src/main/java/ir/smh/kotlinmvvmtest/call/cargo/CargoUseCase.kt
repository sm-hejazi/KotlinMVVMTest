package ir.smh.kotlinmvvmtest.call.cargo

import io.reactivex.Single
import ir.smh.kotlinmvvmtest.call.BaseUseCase
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.data.repository.cargo.CargoRepository
import javax.inject.Inject

class CargoUseCase @Inject
constructor(private val cargoRepository: CargoRepository) : BaseUseCase<List<CargoModel>>() {
    private var pageNumber: Int? = null

    fun setParameters(pageNumber: Int?): CargoUseCase {
        this.pageNumber = pageNumber
        return this
    }

    public override fun buildUseCaseObservable(): Single<List<CargoModel>> {
        return cargoRepository.getCargoItems(pageNumber)
    }
}
