package ir.smh.kotlinmvvmtest.di.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by sharifi on 3/4/18.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScope
