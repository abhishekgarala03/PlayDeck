package app.abhishekgarala.playdeck.features.movie.domain.entities

data class DetailMovieEntity(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val popularity: Double?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val backdropPath: String?,
    val posterPath: String?,
    val genres: List<GenreEntity>,
    val productionCompanies: List<ProductionCompanyEntity>,
    val productionCountries: List<ProductionCountryEntity>,
    val spokenLanguages: List<SpokenLanguageEntity>
)

data class ProductionCompanyEntity(
    val id: Int?,
    val name: String?,
    val logoPath: String?,
    val originCountry: String?
)

data class ProductionCountryEntity(
    val iso31661: String?,
    val name: String?
)

data class SpokenLanguageEntity(
    val englishName: String?,
    val iso6391: String?,
    val name: String?
)
