package ir.smh.kotlinmvvmtest.ui

import com.ppp_smh.initlibrary.util.navigator.BaseNavigator
import ir.smh.kotlinmvvmtest.R
import ir.smh.kotlinmvvmtest.ui.cargo.CargoFragment
import java.lang.ref.WeakReference


/**
 * Created by m.hejazi on 5/14/18.
 */

class DashboarNavigator(activity: DashboardActivity) : BaseNavigator<DashboardActivity> {
    private val mActivity: WeakReference<DashboardActivity>

    init {
        mActivity = WeakReference(activity)
    }


    fun openShopFragment() {
        try {
            getSFragmentTransaction()
                .replace(R.id.dashboarContainer, CargoFragment.newInstance(), "cargo")
                .commitAllowingStateLoss()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

    }

    override fun getActivity(): WeakReference<DashboardActivity> {
        return mActivity
    }
}
