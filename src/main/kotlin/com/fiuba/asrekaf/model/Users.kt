package com.fiuba.asrekaf.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long = 0,
    @get: NotBlank val username : String = "",
    @get: NotBlank val password : String = "",
    @get: NotBlank val code : String = "",
    @get: NotBlank val apiKey: String = ""
)
