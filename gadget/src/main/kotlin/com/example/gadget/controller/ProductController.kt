package com.example.gadget.controller

import com.example.gadget.dto.ProductUpdateDto
import com.example.gadget.model.Product
import com.example.gadget.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product>{
        val product = service.createProduct(product)

        return ResponseEntity.ok(product)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Product>> = ResponseEntity.ok(service.findAllProducts())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<Product>{
     return ResponseEntity.ok(service.findById(id))
    }
    @PutMapping
    fun updateById(@RequestBody productUpdateDto: ProductUpdateDto): ResponseEntity<Product> {
        return ResponseEntity.ok(service.updateById(productUpdateDto))
    }

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<String>{
        service.deleteById(id)
        return ResponseEntity.ok(id)
    }


}