package com.fiuba.asrekaf.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "journals")
data class Journal(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long = 0,
    val title : String = "",
    val content : String = ""){
}