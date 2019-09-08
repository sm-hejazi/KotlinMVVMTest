package ir.smh.kotlinmvvmtest.util.recycleViewScroll

/**
 * Copyright (c) 2017 Fueled. All rights reserved.
 *
 * @author chetansachdeva on 28/11/17
 */

object RecyclerViewUtil {

    fun computeLayoutManagerType(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager): LayoutManagerType {
        return when (layoutManager) {
            is androidx.recyclerview.widget.GridLayoutManager -> LayoutManagerType.GRID
            is androidx.recyclerview.widget.LinearLayoutManager -> LayoutManagerType.LINEAR
            is androidx.recyclerview.widget.StaggeredGridLayoutManager -> LayoutManagerType.STAGGERED_GRID
            else -> LayoutManagerType.DEFAULT
        }
    }

    fun computeVisibleThreshold(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager,
                                layoutManagerType: LayoutManagerType, visibleThreshold: Int): Int =
            when (layoutManagerType) {
                LayoutManagerType.GRID -> (layoutManager as androidx.recyclerview.widget.GridLayoutManager).spanCount * visibleThreshold
                LayoutManagerType.STAGGERED_GRID -> (layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager).spanCount * visibleThreshold
                LayoutManagerType.LINEAR, LayoutManagerType.DEFAULT -> visibleThreshold
            }

    fun getLastVisibleItemPosition(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager,
                                   layoutManagerType: LayoutManagerType): Int =
            when (layoutManagerType) {
                LayoutManagerType.LINEAR, LayoutManagerType.GRID -> (layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition()
                LayoutManagerType.STAGGERED_GRID -> {
                    val lastVisibleItemPositions = (layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                    getStaggeredLayoutLastVisibleItem(lastVisibleItemPositions)
                }
                LayoutManagerType.DEFAULT -> 0
            }

    private fun getStaggeredLayoutLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}