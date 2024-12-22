package org.tech.ebookcmp.book.data.mapper

import org.tech.ebookcmp.book.data.database.BookEntity
import org.tech.ebookcmp.book.data.dto.SearchedBookDto
import org.tech.ebookcmp.book.domain.Book

fun SearchedBookDto.toBook() : Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null){
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        }else{
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = language ?: emptyList(),
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}
fun Book.toBookEntity() : BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingAverage = averageRating,
        ratingCount = ratingCount,
        numOfPagesMedian = numPages,
        numEditions = numEditions
    )
}
fun BookEntity.toBook() : Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = ratingAverage,
        ratingCount = ratingCount,
        numPages = numOfPagesMedian,
        numEditions = numEditions
    )
}