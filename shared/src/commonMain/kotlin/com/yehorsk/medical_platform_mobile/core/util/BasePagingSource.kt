package com.yehorsk.medical_platform_mobile.core.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto

class BasePagingSource<T : Any>(
    private val fetch: suspend (
        page: Int,
        pageSize: Int
    ) -> Result<PagedResponseDto<T>, DataError.Remote>,
    private val onError: suspend (Throwable?) -> Unit,
) : PagingSource<Int, T>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, T> {

        val page = params.key ?: 0

        return fetch(page, params.loadSize).fold(
            onSuccess = { response ->
                LoadResult.Page(
                    data = response.content,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.hasNext) page + 1 else null
                )
            },
            onFailure = { error ->
                LoadResult.Error(DataErrorException(error))
            }
        )
    }

    override fun getRefreshKey(
        state: PagingState<Int, T>
    ): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
    }
}