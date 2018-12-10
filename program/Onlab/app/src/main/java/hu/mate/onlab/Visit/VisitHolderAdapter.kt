package hu.mate.onlab.Visit

import android.app.AlertDialog
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hu.mate.onlab.R
import kotlinx.android.synthetic.main.item_visit_recycle.view.*


class VisitAdapter(private val context: Context, private val mVisitData: List<VisitData>) : RecyclerView.Adapter<VisitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_visit_recycle,
            parent, false
        )
        return VisitViewHolder(view)
    }

    override fun onBindViewHolder(holder: VisitViewHolder, position: Int) {
        holder.mIcon.text = mVisitData[position].sender.substring(0, 1)
        holder.mSender.text = mVisitData[position].sender
        holder.mEmailTitle.text = mVisitData[position].title
        holder.mEmailDetails.text = mVisitData[position].details
        holder.mEmailTime.text = mVisitData[position].time

        holder.mFavorite.setColorFilter(ContextCompat.getColor(context,R.color.colorOrange))

        holder.mFavorite.setOnClickListener {
            if (holder.mFavorite.colorFilter != null) {

                AlertDialog.Builder(context)
                    .setTitle(R.string.visit_title)
                    .setMessage(R.string.visit_confirm)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        Toast.makeText(context, "Deleting ...", Toast.LENGTH_SHORT).show()
                        // yesClicked()
                    }
                    .setNegativeButton(android.R.string.no) { _, _ ->
                        Toast.makeText(context, "continue...", Toast.LENGTH_SHORT).show()
                        //noClicked()
                    }
                    .show()
            }
        }

    }

    override fun getItemCount(): Int {
        return mVisitData.size
    }
}

class VisitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mIcon = itemView.tvIcon!!
    var mSender = itemView.tvEmailSender!!
    var mEmailTitle = itemView.tvEmailTitle!!
    var mEmailDetails = itemView.tvEmailDetails!!
    var mEmailTime = itemView.tvEmailTime!!
    // for OnClick
    var mFavorite = itemView.ivFavorite!!
    var mLayout = itemView.layout!!

}