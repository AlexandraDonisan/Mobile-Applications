package en.ubb.trippie_kotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var openingHours: EditText
    private lateinit var ticketPrice: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        editWordView = findViewById(R.id.edit_word)
        openingHours = findViewById(R.id.opening_hours)
        ticketPrice = findViewById(R.id.ticket_price)

        val button =  findViewById<Button>(R.id.button_save)
        button.setOnClickListener{
            val replyIntent = Intent()
            if(TextUtils.isEmpty(editWordView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }else{
                val extras = Bundle()
                val word = editWordView.text.toString()
                val hours = openingHours.text.toString()
                val price = ticketPrice.text.toString()
                extras.putString("Extra_Word",word)
                extras.putString("Extra_Hours",hours)
                extras.putString("Extra_Price",price)
                replyIntent.putExtras(extras)
//                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
//            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
