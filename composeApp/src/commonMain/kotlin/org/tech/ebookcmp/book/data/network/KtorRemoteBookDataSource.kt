package org.tech.ebookcmp.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.tech.ebookcmp.book.data.dto.BookWorkDto
import org.tech.ebookcmp.book.data.dto.SearchResponseDto
import org.tech.ebookcmp.book.domain.Book
import org.tech.ebookcmp.core.data.safeApiCall
import org.tech.ebookcmp.core.domain.DataError
import org.tech.ebookcmp.core.domain.Result

private const val BASE_URL = "https://openlibrary.org"
class KtorRemoteBookDataSource (
    private val httpClient: HttpClient
) : RemoteBookDataSource{
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeApiCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ){
                parameter("q",query)
                parameter("limit",resultLimit)
                parameter("language","eng")
                parameter("fields","key,title,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count,language")
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeApiCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }

}