package com.example.behaviorbasedsafety.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    @DocumentId
    var id : String = "",
    var auditor : String = "",
    var area : String = "",
    var lugar : String = "",
    var fecha : String = "",
    var aReaccion : String = "false",
    var cReaccion : String = "",
    var aPosicion : String = "false",
    var cPosicion : String = "",
    var aEpp : String = "false",
    var cEpp : String = "",
    var aHtta : String = "false",
    var cHtta : String = "",
    var aProcedimiento : String = "false",
    var cProcedimiento : String = "",
    var a5s : String = "false",
    var c5s : String = "",
    var cGenerales : String = "",
    var perSeguras : String = "0",
    var perInseguras : String = "0",
) : Parcelable
