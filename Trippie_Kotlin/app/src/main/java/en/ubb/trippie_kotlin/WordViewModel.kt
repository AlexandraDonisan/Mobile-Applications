package en.ubb.trippie_kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class WordViewModel(application: Application) : AndroidViewModel(application){

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: WordRepository
    // LiveData gives us updated words when they change
    val allWords: LiveData<List<Word>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao, application.applicationContext)
//        allWords = repository.allWords
        allWords = repository.getAllSightseeings()
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a co-routine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
    }

    fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
    }
}

//Created a wrapper insert() method that calls the Repository's insert() method.
//In this way, the implementation of insert() is completely hidden from the UI.
// We don't want insert to block the main thread, so we're launching a new co-routine
// and calling the repository's insert, which is a suspend function. As mentioned,
// ViewModels have a co-routine scope based on their life cycle called viewModelScope,
// which we use here.