package org.tech.ebookcmp.book.data.network

import org.tech.ebookcmp.book.data.dto.BookWorkDto
import org.tech.ebookcmp.book.data.dto.SearchResponseDto
import org.tech.ebookcmp.core.domain.DataError
import org.tech.ebookcmp.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query : String,
        resultLimit : Int? = null,
    ): Result<SearchResponseDto,DataError.Remote>

    suspend fun getBookDetails(bookWorkId : String) : Result<BookWorkDto,DataError.Remote>
}