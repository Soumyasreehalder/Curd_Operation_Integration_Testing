package com.example.gadget.service

import com.example.gadget.dto.ProductUpdateDto
import com.example.gadget.model.Product
import com.example.gadget.repository.ProductRepo
import org.springframework.stereotype.Service

@Service
class ProductService(private val repository: ProductRepo ) {

    fun createProduct(product: Product): Product = repository.save(product)

    fun findAllProducts(): List<Product> =repository.findAll()

    fun findById(id: String): Product = repository.findById(id).orElseThrow { IllegalArgumentException("No product found fo the specific id $id") }

    fun deleteById(id: String) =repository.deleteById(id)

    fun updateById(productUpdateDto: ProductUpdateDto): Product{
        val product = findById(productUpdateDto.id)
        val newQuantity = productUpdateDto.quantity+product.quantity
        val newProduct = product.copy(quantity = newQuantity)

        return repository.save(newProduct)
    }


}