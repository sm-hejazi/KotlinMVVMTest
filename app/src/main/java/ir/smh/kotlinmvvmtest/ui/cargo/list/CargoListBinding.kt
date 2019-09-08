package ir.smh.kotlinmvvmtest.ui.cargo.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.util.recycleViewScroll.RecyclerViewScrollCallback

object CargoListBinding {

    @BindingAdapter(value = ["products", "onState"], requireAll = false)
    fun setProducts(
        recyclerView: RecyclerView,
        cargoModels: List<CargoModel>, onState: String
    ) {
        val adapter = recyclerView.adapter

        //check if adapter object isn't null and is from PersonAdapter
        if (adapter != null && adapter is CargoAdapter) {
            adapter.addMoreProducts(cargoModels)
        } else
            throw IllegalStateException(
                "RecyclerView either" +
                        " has no adapter set or the "
                        + "adapter isn't of type MovieAdapter"
            )
    }

    /**
     * @param recyclerView       RecyclerView to bind to RecyclerViewScrollCallback
     * @param visibleThreshold   The minimum number of items to have below your current scroll position before loading more.
     * @param resetLoadingState  Reset endless scroll listener when performing a new search
     * @param onScrolledListener OnScrolledListener for RecyclerView scrolled
     */
    @BindingAdapter(
        value = ["visibleThreshold", "resetLoadingState", "onScrolledToBottom"],
        requireAll = false
    )
    fun setRecyclerViewScrollCallback(
        recyclerView: RecyclerView, visibleThreshold: Int, resetLoadingState: Boolean?,
        onScrolledListener: RecyclerViewScrollCallback.OnScrolledListener
    ) {

        val callback = RecyclerViewScrollCallback.Builder(recyclerView.layoutManager!!)
            .visibleThreshold(visibleThreshold)
            .onScrolledListener(onScrolledListener)
            .resetLoadingState(resetLoadingState!!)
            .build()

        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(callback)
    }
}
