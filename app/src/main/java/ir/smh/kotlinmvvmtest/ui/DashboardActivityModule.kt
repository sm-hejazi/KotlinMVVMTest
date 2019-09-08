package ir.smh.kotlinmvvmtest.ui

import dagger.Module
import dagger.Provides
import ir.smh.kotlinmvvmtest.di.scope.ActivityScope

/**
 * Created by m.hejazi on 5/14/18.
 */
@Module
class DashboardActivityModule {

    @Provides
    @ActivityScope
    fun provideNavigator(dashboardActivity: DashboardActivity): DashboarNavigator {
        return DashboarNavigator(dashboardActivity)
    }
}
