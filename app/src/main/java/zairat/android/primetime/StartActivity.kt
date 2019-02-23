package zairat.android.primetime

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        max_number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                start_button.isEnabled = s.isNotEmpty()
            }
        })

        start_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("maxNumber",Integer.parseInt(max_number.text.toString()))
            startActivity(intent)
        }
    }
}
