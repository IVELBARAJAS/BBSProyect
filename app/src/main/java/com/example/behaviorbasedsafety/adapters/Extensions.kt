package com.example.behaviorbasedsafety.adapters

import com.example.behaviorbasedsafety.activities.SearchActivity
import com.example.behaviorbasedsafety.data.model.Card
import com.google.firebase.firestore.QuerySnapshot

fun QuerySnapshot.toListCards(): List<Card>{

    var lst = mutableListOf<Card>()

    for(document in this){
        lst.add(document.toObject(Card::class.java))
    }

    return lst
}