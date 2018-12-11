package zairat.android.primetime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import java.util.concurrent.TimeUnit
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var dispose: Disposable? = null

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

        //automatically scroll through items
        autoScroll( 1500)

        prime_number_recycler.setOnItemClickListener{
            parent: AdapterView<*>, view: View?, position: Int, id: Long ->
            //change activity
            val intent = Intent(applicationContext, DiffList::class.java)
            intent.putExtra("diff_url", pullList[position].diff_url)
            startActivity(intent)

        }
    }

    //block all swiping
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            // true: consume touch event
            // false: dispatch touch event
            MotionEvent.ACTION_UP -> return false
            MotionEvent.ACTION_DOWN -> return false
        }
        return true
    }


    private fun autoScroll( intervalInMillis: Long) {
        dispose?.let {
            if(!it.isDisposed) return
        }
        dispose = Flowable.interval(intervalInMillis, TimeUnit.MILLISECONDS)
                .map { it + 1 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    prime_number_recycler.smoothScrollToPosition(it.toInt() + 1)
                }
    }

//    private fun stopAutoScroll() {
//        dispose?.let(Disposable::dispose)
//    }

}
