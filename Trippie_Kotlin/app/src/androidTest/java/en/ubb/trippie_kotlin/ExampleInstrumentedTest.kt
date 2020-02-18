package en.ubb.trippie_kotlin

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private var wordDao : WordDao? = null
    private lateinit var db: WordRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WordRoomDatabase::class.java).build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val word1 = Word("Central Station", "all day", 12, -1)
        val word2 = Word("Tour Eiffel", "all day", 50, -1)
        val word3 = Word("White House", "9:00 - 12:00", 25, -1)
        runBlocking {
            wordDao?.insert(word1)
            wordDao?.insert(word2)
            wordDao?.insert(word3)}
        val byName = db.wordDao().getSightseeingByName("Central Station")
        assertThat(byName, equalTo(word1))
        assertEquals(wordDao?.getSize(), 3)
    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("en.ubb.trippie_kotlin", appContext.packageName)
    }

//    @Test
//    fun add(){
//        lateinit var wordViewModel: WordViewModel
//        val application = InstrumentationRegistry.getInstrumentation().context.applicationContext as Application
//        wordViewModel = WordViewModel(application)
////        wordViewModel == ViewModelProvider(application).get(WordViewModel::class.java)
//        val word1 = Word("Central Station", "all day", 12, -1)
//        val word2 = Word("Tour Eiffel", "all day", 50, -1)
//        val word3 = Word("White House", "9:00 - 12:00", 25, -1)
//        wordViewModel.insert(word1)
//        wordViewModel.insert(word2)
//        wordViewModel.insert(word3)
//        assertEquals(wordViewModel.allWords.value!![0].word, "Central Station")
//    }
}
