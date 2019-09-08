package ir.smh.kotlinmvvmtest.di.component

import android.app.Application

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ir.smh.kotlinmvvmtest.KotlinMvvmTestApp
import ir.smh.kotlinmvvmtest.di.builder.ActivityBuilder
import ir.smh.kotlinmvvmtest.di.module.AppModule

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBuilder::class])
interface AppComponent {
    fun inject(app: KotlinMvvmTestApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }
}
