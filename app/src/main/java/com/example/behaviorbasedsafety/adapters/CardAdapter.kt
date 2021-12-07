package com.example.behaviorbasedsafety.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.behaviorbasedsafety.R
import com.example.behaviorbasedsafety.databinding.ItemBbsBinding
import com.example.behaviorbasedsafety.data.model.Card
import com.squareup.picasso.Picasso

class CardAdapter(cardList: List<Card>, private val listener:(Card) -> Unit): RecyclerView.Adapter<CardAdapter.ViewHolder>() {


    private var cardList:List<Card> = cardList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bbs, parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val card = cardList[position]

        //holder.imageDog =
        holder.binding.txtCardId.text = card.id
        holder.binding.txtCardArea.text = card.area
        holder.binding.txtCardMachine.text = card.lugar
        holder.binding.txtCardDate.text = card.fecha
        holder.binding.txtCardUser.text = card.auditor

        holder.itemView.setOnClickListener {
            listener(card)
        }

        //Picasso.get().load(dog.imageURL).into(holder.binding.imgDog)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        //val textDog: TextView = v.findViewById(R.id.text_dog)
        //val imageDog: ImageView = v.findViewById(R.id.img_dog)

        val binding = ItemBbsBinding.bind(v)
    }
}