package zairat.android.primetime

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.num_card.view.*

class PrimeAdapter(private val nums: Array<Int>)
    : RecyclerView.Adapter<CustomViewHolder>() {

    //returns number of items in list
    override fun getItemCount(): Int {
        return nums.size
    }

    //To change body of created functions use File | Settings | File Templates.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val primeContainer = layoutInflater.inflate(R.layout.num_card, parent, false)
        return CustomViewHolder(primeContainer)
    }

    //To change body of created functions use File | Settings | File Templates.
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.number_text.text = nums[position].toString()
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)