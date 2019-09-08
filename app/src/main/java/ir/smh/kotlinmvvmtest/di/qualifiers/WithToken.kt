package ir.smh.kotlinmvvmtest.di.qualifiers

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Qualifier

@Qualifier
@Retention(RUNTIME)
annotation class WithToken
