package zairat.android.primetime

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import java.util.concurrent.TimeUnit
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var dispose: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prime_number_recycler.layoutManager = LinearLayoutManager(this)

        val i = intent
        val max = i.getIntExtra("maxNumber", 100)
        val numberOfNumbers = 50
        val numbers = Array(numberOfNumbers){Random.nextInt(2, max+1)}
        val isPrime  = BooleanArray(max-1) {true}

        //initialize points
        var points = 0
        points_text.text = points.toString()

        //use sieve to create isPrime, an array which stores which numbers are prime and which aren't
        for (i in isPrime.indices){
            if (isPrime[i]){
                var temp = (i+2)*(i+2)
                while (temp <= max){
                    isPrime[temp-2] = false
                    temp += (i+2)
                }
            }
        }

        //handle onTouch events in the recyclerview through the adapter
        prime_number_recycler.adapter = PrimeAdapter(numbers, onNumberSelected = {
            //if correct
            if(isPrime[it-2]){
                points++
                points_text.text = points.toString()
            }
            //if not correct
            else{
                Log.i("event listener", "wrong! dumb dumb" )
                stopAutoScroll()
                endGameDialog(false, "Sorry, $it is not a prime number")

            }
        })

        //automatically scroll through items
        autoScroll( 1500)
    }

    //block all swiping
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action){
            MotionEvent.ACTION_MOVE -> return true
        }
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            onUserInteraction()
        }
        if (window.superDispatchTouchEvent(ev)) {
            return true
        }
        return false
    }

    //scrolls through items in a recyclerview. moves on to next item at every interval
    private fun autoScroll( intervalInMillis: Long) {
        dispose?.let {
            if(!it.isDisposed) return
        }
        dispose = Flowable.interval(intervalInMillis, TimeUnit.MILLISECONDS)
                .map { it + 1 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    prime_number_recycler.smoothScrollToPosition(it.toInt())
                }
    }

    private fun stopAutoScroll() {
        dispose?.let(Disposable::dispose)
    }


    private fun endGameDialog(win: Boolean, message: String){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Game Over")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Play Again"){ _, _->
                    //return to start page
                    val intent = Intent(this, StartActivity::class.java)
                    startActivity(intent)
                }
        val alert = dialog.create()
        alert.show()
    }

}
