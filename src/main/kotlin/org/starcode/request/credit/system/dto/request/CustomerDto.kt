package org.starcode.request.credit.system.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import org.starcode.request.credit.system.entities.Address
import org.starcode.request.credit.system.entities.Customer
import java.math.BigDecimal

data class CustomerDto(

    @field:NotEmpty(message = "The 'firstName' field cannot be null")
    val firstName: String,

    @field:NotEmpty(message = "The 'lastName' field cannot be null")
    val lastName: String,

    @field:CPF(message = "This is not a valid CPF!")
    val cpf: String,

    @field:NotNull(message = "The 'income' field cannot be null")
    val income: BigDecimal,

    @field:NotEmpty(message = "The 'email' field cannot be null")
    @field:Email(message = "This is not a valid email!")
    val email: String,

    @field:NotEmpty(message = "The 'password' field cannot be null")
    val password: String,

    @field:NotEmpty(message = "The 'zipCode' field cannot be null")
    val zipCode: String,

    @field:NotEmpty(message = "The 'street' field cannot be null")
    val street: String

) {

    fun toEntity(): Customer = Customer(
            firstName = this.firstName,
            lastName = this.lastName,
            cpf = this.cpf,
            email = this.email,
            password = this.password,
            income = this.income,
            address = Address(
                zipCode = this.zipCode,
                street = this.street
            )
        )
}
