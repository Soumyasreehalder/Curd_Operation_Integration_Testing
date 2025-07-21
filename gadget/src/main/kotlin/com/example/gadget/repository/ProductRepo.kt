package com.example.gadget.repository

import com.example.gadget.model.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepo: MongoRepository<Product, String>