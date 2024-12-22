package org.tech.ebookcmp.book.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavoriteBookDatabase>{
    override fun initialize(): FavoriteBookDatabase

}