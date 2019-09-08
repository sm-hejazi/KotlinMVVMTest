package ir.smh.kotlinmvvmtest.ui

import com.ppp_smh.initlibrary.ui.base.BaseViewModel
import com.ppp_smh.initlibrary.util.connectivity.BaseConnectionManager

import javax.inject.Inject


/**
 * Created by m.hejazi on 5/14/18.
 */

class DashboardVM @Inject
constructor(
    connectionManager: BaseConnectionManager,
    private val dashboarNavigator: DashboarNavigator
) : BaseViewModel(connectionManager, dashboarNavigator) {

    fun openCargo() {
        dashboarNavigator.openShopFragment()
    }

    fun <T> getCurrentFragment(tag: String): T? {
        try {
            return dashboarNavigator.fragmentManager.findFragmentByTag(tag) as T?
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

        return null
    }

    override fun onClear() {
        super.onClear()
    }

    override fun clearUseCaseDisposables() {

    }
}
