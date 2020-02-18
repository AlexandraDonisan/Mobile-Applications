package en.ubb.trippie_kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Word::class), version = 1)
public abstract class WordRoomDatabase : RoomDatabase(){
    abstract fun wordDao() : WordDao

    private class WordDatabaseCallBack(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
//                    populateDatabase(database.wordDao())
                }
            }
        }
        suspend fun populateDatabase(wordDao: WordDao ){
            //Delete all content here
            wordDao.deleteAll()

            //Add sample words
            var word = Word("Rijksmuseum","9:00 - 16:00",20)
            wordDao.insert(word)
            word = Word("Dam Squre","9:00 - 16:00",20)
            wordDao.insert(word)
            word = Word("The Royal Palace","9:00 - 16:00",20)
            wordDao.insert(word)
            word = Word("Van Gogh Museum", "9:00 - 16:00",20)
            wordDao.insert(word)
            word = Word("Anne Frank Museum", "9:00 - 16:00",20)
            wordDao.insert(word)
            word = Word("Oude Kerk", "9:00 - 16:00",20)
            wordDao.insert(word)
        }
    }

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}



//The database class for Room must be abstract and extend RoomDatabase
//We annotate the class to be a Room database with @Database and declare
//      the entities that belong in the database and set the version number.
//      Listing the entities creates tables in the database.
//The database provides the DAOs that work with the database. To do that,
//      you create an abstract "getter" method for each @Dao.
//We've defined a singleton, WordRoomDatabase, to prevent having multiple
//      instances of the database opened at the same time.
//getDatabase returns the singleton. It'll create the database the first time
//      it's accessed, using Room's database builder to create a RoomDatabase object
//      in the application context from the WordRoomDatabase class and names it "word_database".