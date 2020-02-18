package en.ubb.trippie_kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_see_details.*

class SeeDetailsActivity : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var openingHours: TextView
    private lateinit var ticketPrice: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_details)

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        travel_view.setOnClickListener {
            travel_view.animate()
                .scaleX(0.5f)
                .scaleY(0.5f)
                .duration = 2000
            travel_view.clearAnimation()
        }


        name = findViewById(R.id.view_name)
        openingHours = findViewById(R.id.view_schedule)
        ticketPrice = findViewById(R.id.view_price)

        val extras = intent.extras
        name.text = extras?.getString("name")
        openingHours.text = "Schedule: " + extras?.getString("hours")
        ticketPrice.text = "Price: " +  extras?.getInt("price").toString()

    }
}
