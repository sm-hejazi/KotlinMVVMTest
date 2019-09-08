package ir.smh.kotlinmvvmtest.ui.cargo

import android.os.Bundle
import android.widget.CompoundButton
import androidx.core.util.Pair
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import com.ppp_smh.initlibrary.entity.ErrorStatus.ErrorModel
import com.ppp_smh.initlibrary.ui.base.BaseViewModel
import com.ppp_smh.initlibrary.util.connectivity.BaseConnectionManager
import io.reactivex.functions.Consumer
import ir.smh.kotlinmvvmtest.call.cargo.CargoUseCase
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.ui.DashboarNavigator
import javax.inject.Inject

//import ir.smh.kotlinmvvmtest.ui.shopproduct.ShopProductActivity;

/**
 * Created by m.hejazi on 5/14/18.
 */

class CargoVM @Inject
constructor(
    connectionManager: BaseConnectionManager,
    private val dashboarNavigator: DashboarNavigator,
    private val cargoUseCase: CargoUseCase
) : BaseViewModel(connectionManager, dashboarNavigator) {
    val responseProductModels: ObservableList<CargoModel>
    private val existSwitch: CompoundButton? = null


    init {
        responseProductModels = ObservableArrayList()
    }

    fun getProducts(pageNumber: Int?) {
        isLoadingBg.set(true)
        cargoUseCase
            .setParameters(pageNumber)
            .execute(Consumer { this.requestCargoResponse(it) })
    }

    private fun requestCargoResponse(response: Pair<List<CargoModel>, ErrorModel>) {
        isLoading.set(false)
        isLoadingBg.set(false)
        if (response.second != null) {
            if (response.second!!.code == -1);
            return
        }

        if (response.first != null && response.first!!.size > 0) {
            setProductModels(response.first!!)
        }
    }


    fun detailProduct(product: CargoModel) {
        val bundle = Bundle()
        //        bundle.putParcelable(ShopProductActivity.KEY_EXTRA_PRODUCT, product);
        //        dashboarNavigator.startActivity(ShopProductActivity.class, bundle);
    }

    override fun clearUseCaseDisposables() {
        cargoUseCase.dispose()
    }

    override fun getIsLoading(): ObservableBoolean {
        return isLoading
    }

    fun setProductModels(cargoModels: List<CargoModel>) {
        responseProductModels.clear()
        responseProductModels.addAll(cargoModels)
    }

    fun onLoadMore(page: Int) {
        getProducts(page)
    }

    companion object {
        private val TAG = CargoVM::class.java.simpleName
    }
}
