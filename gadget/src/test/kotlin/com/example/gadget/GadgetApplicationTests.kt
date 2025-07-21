package com.example.gadget

import com.example.gadget.dto.ProductUpdateDto
import com.example.gadget.model.Product
import com.example.gadget.repository.ProductRepo
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.client.RestClient
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.junit.jupiter.api.Assertions.assertEquals

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GadgetApplicationTests{

	companion object {
		@Container
		val mongo = MongoDBContainer("mongo:latest").apply {
			start()
		}

		@JvmStatic
		@DynamicPropertySource
		fun mongoProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl)
		}
	}

	@Autowired
	lateinit var  repo: ProductRepo
	@Autowired
	lateinit var restClientBuilder: RestClient.Builder
	@LocalServerPort
	val port: Int=0


		lateinit var client: RestClient

	@BeforeEach
	fun setup() {

		client = restClientBuilder.baseUrl("http://localhost:$port")
			.build()
		repo.deleteAll()}

		@Test
		fun `should create product`() {
			//given
			val req = Product(name ="mouse", price = 500.00, quantity = 200)

			//when
			val res = client.post()
				.uri("/products")
				.body(req)
				.retrieve()
				.toEntity(Product::class.java)

			//then
			assertEquals(HttpStatus.OK, res.statusCode)
			assertEquals("mouse", res.body?.name)

		}

		@Test
		fun `should read product`() {
			val saved = repo.save(Product(name ="Camera", price = 1099.0, quantity = 12))

			val res = client.get()
				.uri("/products/${saved.id}")
				.retrieve()
				.toEntity(Product::class.java)

			assertEquals(HttpStatus.OK, res.statusCode)
			assertEquals("Camera", res.body?.name)

		}

		@Test
		fun `should update product`() {
			val saved = repo.save(Product(name = "Headphones", price = 199.0, quantity = 10))
			val update = ProductUpdateDto(id=saved.id.toString(),quantity = 20)

			val res = client.put()
				.uri("/products")
				.body(update)
				.retrieve()
				.toEntity(Product::class.java)

			assertEquals(HttpStatus.OK, res.statusCode)
			assertEquals(30, res.body?.quantity)

		}

		@Test
		fun `should delete product`() {
			val saved = repo.save(Product(name = "bag", price = 499.0, quantity = 12))

			val res = client.delete()
				.uri("/products/${saved.id}")
				.retrieve()
				.toBodilessEntity()

			assertEquals(HttpStatus.OK, res.statusCode)
			println("Response: ${res.statusCode}")
			println("Body: ${res.body}")

		}
	}
