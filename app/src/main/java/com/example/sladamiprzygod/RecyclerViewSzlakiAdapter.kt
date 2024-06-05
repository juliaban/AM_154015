package com.example.sladamiprzygod

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSzlakiAdapter(private val activity: NextActivity, private val szlakiLista: MutableList<Szlak>) :
    RecyclerView.Adapter<RecyclerViewSzlakiAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_szlaki_lista_element, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return szlakiLista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val szlak = szlakiLista[position]
        holder.tvSzlakTitle.text = szlak.title
        val resourceId = activity.resources.getIdentifier(szlak.image, "drawable", activity.packageName)
        holder.ivSzlakImg.setImageResource(resourceId)

        holder.cardView.setOnClickListener {
            val intent = Intent(activity, DetailsActivity::class.java).apply {
                putExtra("title", szlak.title)
                putExtra("image", szlak.image)
                putExtra("description", szlak.description)
                putExtra("time", szlak.time)
            }
            activity.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSzlakTitle: TextView = itemView.findViewById(R.id.tvSzlakiTitle)
        val ivSzlakImg: ImageView = itemView.findViewById(R.id.ivSzlakiImg)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}