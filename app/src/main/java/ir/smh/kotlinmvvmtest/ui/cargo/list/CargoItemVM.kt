package ir.smh.kotlinmvvmtest.ui.cargo.list

import androidx.databinding.BaseObservable

import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel

class CargoItemVM(cargoModel: CargoModel) : BaseObservable() {

    private var cargo: CargoModel? = null

    init {
        cargo = cargoModel
    }

    fun getCargo(): CargoModel? {
        return cargo
    }
}
