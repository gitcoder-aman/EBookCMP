package org.tech.ebookcmp.book.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
     fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}