package ir.smh.kotlinmvvmtest.ui


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.smh.kotlinmvvmtest.ui.cargo.CargoFragment
import ir.smh.kotlinmvvmtest.ui.cargo.CargoFragmentModule

/**
 * Created by m.hejazi on 5/14/18.
 */
@Module
abstract class DashboardFragmentProvider {

    @ContributesAndroidInjector(modules = [CargoFragmentModule::class])
    abstract fun provideMyShopFragment(): CargoFragment
}
