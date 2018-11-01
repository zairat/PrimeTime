package zairat.android.primetime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prime_number_recycler.layoutManager = LinearLayoutManager(this)

        val max = 25
        val numbers = Array(max-1){i -> i+2}
        val isPrime  = BooleanArray(max-1) {true}

        for (i in numbers.indices){
            if (isPrime[i]){
                var temp = (i+2)*(i+2)
                while (temp <= max){
                    isPrime[temp-2] = false
                    temp += (i+2)
                }
            }
        }

        prime_number_recycler.adapter = PrimeAdapter(numbers)
    }
}
