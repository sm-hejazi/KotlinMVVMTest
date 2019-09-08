package ir.smh.kotlinmvvmtest.di.builder


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.smh.kotlinmvvmtest.di.scope.ActivityScope
import ir.smh.kotlinmvvmtest.ui.DashboardActivity
import ir.smh.kotlinmvvmtest.ui.DashboardActivityModule
import ir.smh.kotlinmvvmtest.ui.DashboardFragmentProvider

/**
 * Created by m.hejazi on 2/13/2018.
 */
@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [DashboardActivityModule::class, DashboardFragmentProvider::class])
    internal abstract fun bindDashboardActivity(): DashboardActivity

    /*    @ActivityScope
    @ContributesAndroidInjector(modules = {RegisterActivityModule.class, RegisterFragmentProvider.class})
    abstract RegisterActivity bindRegisterActivity();*/

}
