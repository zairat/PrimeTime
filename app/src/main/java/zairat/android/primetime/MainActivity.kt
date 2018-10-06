package zairat.android.primetime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}
