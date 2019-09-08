package ir.smh.kotlinmvvmtest

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ir.smh.kotlinmvvmtest.di.component.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by m.hejazi on 5/7/18.
 */

class KotlinMvvmTestApp : Application(), HasActivityInjector {
    var isLogin = true

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this


        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        initKianApp()
    }

    private fun initKianApp() {

    }


    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    companion object {
        var instance: KotlinMvvmTestApp? = null
            private set
    }
}
