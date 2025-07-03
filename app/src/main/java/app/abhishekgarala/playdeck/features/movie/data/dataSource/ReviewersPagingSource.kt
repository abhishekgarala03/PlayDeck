package app.abhishekgarala.playdeck.features.movie.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.MovieApi
import app.abhishekgarala.playdeck.features.movie.domain.entities.ReviewResultEntity
import retrofit2.HttpException
import java.io.IOException

class ReviewersPagingSource(
    private val api: MovieApi,
    private val movieId: Int,
) : PagingSource<Int, ReviewResultEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewResultEntity> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = api.getReviews(
                movieId = movieId,
                page = pageIndex
            )
            val movies = response.body()
            val nextKey =
                if (movies?.results?.isEmpty() == true) {
                    null
                } else {
                    pageIndex.plus(1)
                }
            LoadResult.Page(
                data = movies?.results?.map { it.toEntity() } ?: emptyList(),
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex.minus(1),
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewResultEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val pagingState = state.closestPageToPosition(anchorPosition)
            pagingState?.prevKey?.plus(1) ?: pagingState?.nextKey?.minus(1)
        }
    }
}