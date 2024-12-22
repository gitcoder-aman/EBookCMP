package org.tech.ebookcmp.book.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tech.ebookcmp.book.data.database.FavoriteBookDao
import org.tech.ebookcmp.book.data.mapper.toBook
import org.tech.ebookcmp.book.data.mapper.toBookEntity
import org.tech.ebookcmp.book.data.network.RemoteBookDataSource
import org.tech.ebookcmp.book.domain.Book
import org.tech.ebookcmp.book.domain.BookRepository
import org.tech.ebookcmp.core.domain.DataError
import org.tech.ebookcmp.core.domain.EmptyResult
import org.tech.ebookcmp.core.domain.Result
import org.tech.ebookcmp.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository{
   override suspend fun searchBooks(query : String) :Result<List<Book>,DataError.Remote>{
        return remoteBookDataSource
            .searchBooks(query)
            .map {dto->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBookById(bookId)

        return if(localResult == null){
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        }else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(bookId: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map {bookEntities->
                bookEntities.any { it.id == bookId }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        }catch (e : SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(bookId: String) {
        favoriteBookDao.deleteFavoriteBookById(
            id = bookId
        )
    }
}