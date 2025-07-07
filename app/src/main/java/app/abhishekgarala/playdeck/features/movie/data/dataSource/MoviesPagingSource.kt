package app.abhishekgarala.playdeck.features.movie.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.MovieApi
import app.abhishekgarala.playdeck.features.movie.domain.entities.MovieResultEntity
import retrofit2.HttpException
import java.io.IOException

internal const val TMDB_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val api: MovieApi
) : PagingSource<Int, MovieResultEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResultEntity> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = api.getMovies(
                page = pageIndex
            )
            val movies = response.body()
            LoadResult.Page(
                data = movies?.results?.map { it.toResultsMovieEntity() } ?: emptyList(),
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = pageIndex + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResultEntity>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }
}