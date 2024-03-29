package ir.smh.kotlinmvvmtest.util.recycleViewScroll

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewScrollCallback(private val visibleThreshold: Int, private val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager)
    : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    // The current offset index of data you have loaded
    private var currentPage = 0
    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    private var loading = true
    // Sets the starting page index
    private val startingPageIndex = 0

    lateinit var layoutManagerType: LayoutManagerType
    lateinit var onScrolledListener: OnScrolledListener

    constructor(builder: Builder) : this(builder.visibleThreshold, builder.layoutManager) {
        this.layoutManagerType = builder.layoutManagerType
        this.onScrolledListener = builder.onScrolledListener
        if (builder.resetLoadingState) {
            resetState()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = RecyclerViewUtil.getLastVisibleItemPosition(layoutManager, layoutManagerType)
        val totalItemCount = layoutManager.itemCount

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onScrolledListener.onScrolledToBottom(currentPage)
            loading = true
        }
    }
    // Call this method whenever performing new searches
    private fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    interface OnScrolledListener {
        fun onScrolledToBottom(page: Int)
    }

    class Builder(internal val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager) {
        internal var visibleThreshold = 7
        internal var layoutManagerType = LayoutManagerType.LINEAR
        internal lateinit var onScrolledListener: OnScrolledListener
        internal var resetLoadingState: Boolean = false

        fun visibleThreshold(value: Int): Builder {
            visibleThreshold = value
            return this
        }

        fun onScrolledListener(value: OnScrolledListener): Builder {
            onScrolledListener = value
            return this
        }

        fun resetLoadingState(value: Boolean): Builder {
            resetLoadingState = value
            return this
        }

        fun build(): RecyclerViewScrollCallback {
            layoutManagerType = RecyclerViewUtil.computeLayoutManagerType(layoutManager)
            visibleThreshold = RecyclerViewUtil.computeVisibleThreshold(
                    layoutManager, layoutManagerType, visibleThreshold)
            return RecyclerViewScrollCallback(this)
        }
    }
}