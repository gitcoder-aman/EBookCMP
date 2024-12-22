package org.tech.ebookcmp.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
//        val dbFile = documentDirectory() + "/${FavoriteBookDatabase.DB_NAME}"
        return Room.databaseBuilder<FavoriteBookDatabase>(name = "dbFile")
    }
//    private fun documentDirectory() : String{
//        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
//            directory = NSDocumentDirectory,
//            inDomain = NSUserDomainMask,
//            appropriateForURL = null,
//            create = false,
//            error = null
//        )
//        return requireNotNull(documentDirectory?.path)
//    }
}