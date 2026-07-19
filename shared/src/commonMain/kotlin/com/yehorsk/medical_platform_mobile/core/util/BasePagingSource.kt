package com.yehorsk.medical_platform_mobile.core.util

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<T : Any>(
    private val pageSize: Int = 20,
    private val fetch: suspend (page: Int, pageSize: Int) -> Result<List<T>, DataError.Remote>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 0
        return fetch(page, pageSize)
            .fold(
                onSuccess = { items ->
                    LoadResult.Page(
                        data = items,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (items.isEmpty() || items.size < pageSize) null else page + 1
                    )
                },
                onFailure = { error -> LoadResult.Error(DataErrorException(error)) }
            )
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
    }
}