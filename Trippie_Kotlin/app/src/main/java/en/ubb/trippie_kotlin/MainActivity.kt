package en.ubb.trippie_kotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel
    private val newWordActivityRequestCode = 1
    private val updateSightseeingActivityRequestCode = 1
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this,
            seeDetailsListener = {startActivity(Intent(this@MainActivity, SeeDetailsActivity::class.java)
                .apply {
                    putExtra("name", it.word)
                    putExtra("hours", it.hours)
                    putExtra("price", it.ticket_price) })},
            deleteListener = {wordViewModel.delete(it)},
            updateListener = {startActivityForResult(Intent(this@MainActivity, UpdateWordActivity::class.java)
                .putExtra("Sightseeing", it)
                , updateSightseeingActivityRequestCode
                )}
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let{ adapter.setWords(it)}
        })

        val signOutButton = findViewById<Button>(R.id.sign_out_button)
        signOutButton.setOnClickListener { view->
            val bounceAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            view.startAnimation(bounceAnimation)
            AuthUI.getInstance().signOut(this@MainActivity)
            val intent = Intent(this@MainActivity, LogInActivity::class.java)
            startActivityForResult(intent, 1)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        ConnectionLiveData(applicationContext).observe(this, Observer {
            isConnected =
                if (it.isConnected) {
                    if (!isConnected)
                        Toast.makeText(applicationContext, "Internet connection now available",
                            Toast.LENGTH_SHORT).show()
                    true
                } else {
                    if (isConnected)
                        Toast.makeText(applicationContext, "Internet connection not available",
                            Toast.LENGTH_SHORT).show()
                    false
                }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == updateSightseeingActivityRequestCode && resultCode == Activity.RESULT_OK && data?.getSerializableExtra("Update_Word") != null){
            data?.let {
                try {
                    val word = it.getSerializableExtra("Update_Word") as Word
                    wordViewModel.update(word)
                }catch (e: Exception){
                    Toast.makeText(
                        applicationContext,
                        e.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }}

        if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK && data?.getStringExtra("Extra_Word") != null){
            data?.let {
                val extras = it.extras
                val nameString = extras!!.getString("Extra_Word")
                val hoursString = extras.getString("Extra_Hours")
                val ticketString = extras.getString("Extra_Price")
                try {
                    val price = ticketString?.toInt()
                    val word = Word(nameString.toString(),hoursString, price)
//                val word = Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY))
                    wordViewModel.insert(word)
                }catch (e: Exception){
                    Toast.makeText(
                        applicationContext,
                        e.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        }
    }
}
