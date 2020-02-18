package en.ubb.trippie_kotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class UpdateWordActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var opening_hours: EditText
    private lateinit var ticket_price: EditText
    private val updateListener: (Word) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_word)

        name = findViewById(R.id.update_name)
        opening_hours = findViewById(R.id.update_opening_hours)
        ticket_price = findViewById(R.id.update_ticket_price)

        val sight = intent.getSerializableExtra("Sightseeing") as Word
        name.setText(sight.word)
        opening_hours.setText(sight.hours)
        ticket_price.setText(sight.ticket_price.toString())

        val button =  findViewById<Button>(R.id.button_update)
        button.setOnClickListener{
            val replyIntent = Intent()
            if(TextUtils.isEmpty(name.text) || TextUtils.isEmpty(opening_hours.text) || TextUtils.isEmpty(ticket_price.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{
                sight.word = name.text.toString()
                sight.hours = opening_hours.text.toString()
                sight.ticket_price = ticket_price.text.toString().toInt()
                replyIntent.putExtra("Update_Word", sight)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
