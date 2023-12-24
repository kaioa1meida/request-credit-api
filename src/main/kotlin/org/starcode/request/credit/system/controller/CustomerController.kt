package org.starcode.request.credit.system.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.starcode.request.credit.system.dto.request.CustomerDto
import org.starcode.request.credit.system.dto.request.CustomerUpdateDto
import org.starcode.request.credit.system.dto.response.CustomerView
import org.starcode.request.credit.system.entities.Customer
import org.starcode.request.credit.system.service.implementation.CustomerService

@RestController
@RequestMapping("/api/customer/")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping("{id}")
    fun findCustomer(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerService.findCustomer(id)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
    }

    @PostMapping
    fun saveCustomer(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<String> {
        val savedUser = customerService.saveCustomer(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer: ${savedUser.email} saved!")
    }

    @PatchMapping
    fun updateCustomer(
        @RequestParam(value = "customerId") id: Long,
        @RequestBody @Valid customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerService.findCustomer(id)
        val customerToUpdate: Customer = customerUpdateDto.toEntity(customer)
        val customerUpdated: Customer = this.customerService.saveCustomer(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
    }

    @DeleteMapping("{id}")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<String> {
        this.customerService.deleteCustomer(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer deleted!")
    }
}