package org.starcode.request.credit.system.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.starcode.request.credit.system.entities.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(

    @field:NotEmpty(message = "The 'firstName' field cannot be null")
    val firstName: String,

    @field:NotEmpty(message = "The 'lastName' field cannot be null")
    val lastName: String,

    @field:NotNull(message = "The 'income' field cannot be null")
    val income: BigDecimal,

    @field:NotEmpty(message = "The 'zipCode' field cannot be null")
    val zipCode: String,

    @field:NotEmpty(message = "The 'street' field cannot be null")
    val street: String

) {

    fun toEntity(customer: Customer) : Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.zipCode = this.zipCode
        customer.address.street = this.street
        return customer
    }
}
