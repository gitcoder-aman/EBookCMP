package org.tech.ebookcmp.book.domain

import kotlinx.coroutines.flow.Flow
import org.tech.ebookcmp.core.domain.DataError
import org.tech.ebookcmp.core.domain.EmptyResult
import org.tech.ebookcmp.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query : String) : Result<List<Book>,DataError.Remote>
    suspend fun getBookDescription(bookId : String) : Result<String?,DataError>

     fun getFavoriteBooks() : Flow<List<Book>>
     fun isBookFavorite(bookId : String) : Flow<Boolean>
     suspend fun markAsFavorite(book: Book) : EmptyResult<DataError.Local>
     suspend fun deleteFromFavorites(bookId : String)

}