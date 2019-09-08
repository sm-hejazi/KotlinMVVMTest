package ir.smh.kotlinmvvmtest.data.repository.cargo

import io.reactivex.Single
import io.reactivex.SingleObserver
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.data.model.cargo.Paging
import java.util.*
import javax.inject.Inject

class CargoRepositoryFactory @Inject
constructor() {

    internal fun getCargoItems(paging: Paging): Single<List<CargoModel>> {
        //        return cloudRepository.getCargoItems(paging);
        val mList = ArrayList<CargoModel>()

        mList.add(
            CargoModel.Builder().id(0).title("آهن").from("تهران").to("شیراز").weight(13).price(
                java.lang.Long.valueOf(1500)
            ).build()
        )
        mList.add(
            CargoModel.Builder().id(0).title("خودرو").from("تهران").to("اصفهان").weight(17).price(
                java.lang.Long.valueOf(16000)
            ).build()
        )
        mList.add(
            CargoModel.Builder().id(0).title("آهن").from("تهران").to("مشهد").weight(27).price(
                java.lang.Long.valueOf(17800)
            ).build()
        )
        mList.add(
            CargoModel.Builder().id(0).title("علوفه").from("تهران").to("شمال").weight(11).price(
                java.lang.Long.valueOf(23456)
            ).build()
        )
        mList.add(
            CargoModel.Builder().id(0).title("چوب").from("تهران").to("شیراز").weight(12).price(
                java.lang.Long.valueOf(1795500)
            ).build()
        )


        return object : Single<List<CargoModel>>() {
            override fun subscribeActual(observer: SingleObserver<in List<CargoModel>>) {
                observer.onSuccess(mList)
            }
        }

    }
}
