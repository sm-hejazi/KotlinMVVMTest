package ir.smh.kotlinmvvmtest.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ppp_smh.initlibrary.ui.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ir.smh.kotlinmvvmtest.R
import ir.smh.kotlinmvvmtest.databinding.ActivityDashboardBinding
import javax.inject.Inject


class DashboardActivity : BaseActivity<DashboardVM>(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var dashboardViewModel: DashboardVM
    private var activityDashboardBinding: ActivityDashboardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        setContentView(activityDashboardBinding!!.getRoot())
        setBaseVM(dashboardViewModel)
    }


    override fun initUI(savedInstanceState: Bundle) {
        super.initUI(savedInstanceState)
        dashboardViewModel!!.openCargo()

    }

    override fun getViewModel(): DashboardVM? {
        return dashboardViewModel
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }
}
