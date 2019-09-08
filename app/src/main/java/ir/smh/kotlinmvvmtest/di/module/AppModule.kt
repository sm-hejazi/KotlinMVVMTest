package ir.smh.kotlinmvvmtest.di.module

import android.app.Application
import android.content.Context
import com.ppp_smh.initlibrary.util.connectivity.BaseConnectionManager
import com.ppp_smh.initlibrary.util.connectivity.ConnectionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideConnectionManager(context: Context): BaseConnectionManager {
        return ConnectionManager(context)
    }
}
