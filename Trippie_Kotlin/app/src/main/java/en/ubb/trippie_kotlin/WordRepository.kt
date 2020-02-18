package en.ubb.trippie_kotlin

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao, private val applicationContext: Context) {
    val apiService by lazy{ ApiService.create() }
    private var isConnected: Boolean = false
    private var disposable: Disposable? = null

    //Room executes all queries on a separate thread.
    //Observed LiveData will notify the observer whe the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()
    val offlineSightseeings : MutableList<Word> = mutableListOf()

    init {
        ConnectionLiveData(applicationContext).observeForever( Observer {
            isConnected = it.isConnected
            if (isConnected){
                GlobalScope.launch {
                    updateDatabase()
                }

//                disposable = apiService.getAllSightseeings()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe{
//                        println("All WORDS $allWords.value")
//                        allWords.value?.forEach { sight ->
//                            if(!it.contains(sight)){
//                                println("Sight $sight")
//                                GlobalScope.launch {wordDao.deleteElement(sight)}
//                            }
//                        }
//                    }
            }
        })
    }

    fun getAllSightseeings(): LiveData<List<Word>> {
        if(isConnected){
            GlobalScope.launch{
                updateDatabase()
            }
        }
        return allWords
    }

    suspend fun updateDatabase(){
        this.allWords.value?.forEach { sight ->
            run{
                if(sight.remote_id == -1) {
                    disposable = apiService.addSightseeing(sight)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            println("Added: $it")
                            sight.remote_id = it.remote_id
                        }
//                    GlobalScope.launch { sight.remote_id = it. }

                }
            }
        }

//        for (sight : Word in offlineSightseeings) {
//            disposable = apiService.addSightseeing(sight)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    println("Added: $it")
//                    offlineSightseeings.remove(it)
//                }
//        }

        disposable = apiService.getAllSightseeings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                println("From Server: $it")
                it.forEach{sight ->
                    if(!(allWords.value)!!.contains(sight)){
                        GlobalScope.launch {
                            wordDao.insert(sight)
                        }
                    }
                }
                this.allWords.value?.forEach { sight ->
                    run {
                        if (!offlineSightseeings.contains(sight) && !it.contains(sight)) {
                            GlobalScope.launch {
                                wordDao.deleteElement(sight)
                            }
                        }
                    }
                }
            }


    }

    suspend fun insert(word: Word){
        if(!isConnected) {
            wordDao.insert(word)
            offlineSightseeings.add(word)

        }
        else{
            disposable = apiService.addSightseeing(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    println("Id of added word ${it.remote_id}")
//                    word.remote_id = it.
                    GlobalScope.launch {
                        wordDao.insert(it)
                        println("inserted word $it")
                    }
                }
        }
    }

    suspend fun delete(word: Word){
        if(isConnected) {
            wordDao.deleteElement(word)
            disposable = apiService.deleteSightseeing(word.remote_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{}
        }
        else
        {
            Toast.makeText(
                applicationContext,
                "Delete action can not be performed without Internet Connection!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    suspend fun update(word: Word){
        if(isConnected) {
            wordDao.updateName(word)
            disposable = apiService.updateightseeing(word.remote_id, word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{}
        }
        else
        {
            Toast.makeText(
                applicationContext,
                "Update action can not be performed without Internet Connection!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun connection() : Boolean {
     ConnectionLiveData(applicationContext).observeForever( Observer {
        isConnected = it.isConnected })
        return isConnected
    }

}


//The DAO is passed into the repository as opposed to the whole database.
//      This is because you only need access to the DAO, since it contains all the
//      read/write methods for the database. There's no need to expose the entire
//      database to the repository.
//The list of words is a public property. It's initialized by getting the LiveData
//      list of words from Room; we can do this because of how we defined the
//      getAlphabetizedWords method to return LiveData in the "The LiveData class" step.
//      Room executes all queries on a separate thread. Then observed LiveData will
//      notify the observer when the data has changed on the main thread.
//The suspend modifier tells the compiler that this needs to be called
//      from a coroutine or another suspending function.