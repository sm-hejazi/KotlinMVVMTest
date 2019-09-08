package ir.smh.kotlinmvvmtest.call

import androidx.core.util.Pair
import com.ppp_smh.initlibrary.entity.ErrorStatus.ErrorModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by m.hejazi on 2/15/2018.
 */
abstract class BaseUseCase<T> protected constructor() {

    val disposables: CompositeDisposable

    init {
        disposables = CompositeDisposable()
    }

    protected abstract fun buildUseCaseObservable(): Single<T>

    fun execute(
        onResponse: Consumer<Pair<T, ErrorModel>>
    ) {

        val single = this.buildUseCaseObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        addDisposable(
            single.subscribe(
                // on success
                { model -> onResponse.accept(Pair<T, ErrorModel>(model, null)) },

                //on error
                { throwable -> onResponse.accept(Pair<T, ErrorModel>(null, ErrorModel(-1,throwable.message)))})
        )
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    interface ExpireTokenCallback {
        fun onTokenExpired(cls: Class<*>)
    }
}
