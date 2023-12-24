package org.starcode.request.credit.system.dto.request

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.entities.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(

    @field:NotNull(message = "The 'creditValue' field cannot be null")
    @field:Min(value = 1000, message = "Values below 1000 R$ are not allowed")
    val creditValue: BigDecimal,

    @field:Future(message = "Past dates are not allowed")
    val dayOfFirstInstallment: LocalDate,

    @field:Min(value = 2, message = "Installments below 2x are not allowed")
    @field:Max(value = 48, message = "Installments above 48x are not allowed")
    @field:NotNull(message = "The 'numberOfInstallments' field cannot be null")
    val numberOfInstallments: Int,

    @field:NotNull(message = "The 'customerId' field cannot be null")
    val customerId: Long

) {
    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayOfFirstInstallment = this.dayOfFirstInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
