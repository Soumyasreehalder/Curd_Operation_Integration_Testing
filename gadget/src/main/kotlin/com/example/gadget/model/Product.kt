package com.example.gadget.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("products")
data class Product(
    val id: String? = null,
    val name: String,
    val price: Double,
    val quantity: Int
)
