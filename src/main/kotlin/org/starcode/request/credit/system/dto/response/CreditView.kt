package org.starcode.request.credit.system.dto.response

import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.utils.Status
import java.math.BigDecimal
import java.util.*

data class CreditView(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallments: Int,
    val status: Status,
    val customerEmail: String?,
    val customerIncome: BigDecimal?
){
    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallments = credit.numberOfInstallments,
        status = credit.creditStatus,
        customerEmail = credit.customer?.email,
        customerIncome = credit.customer?.income
    )
}
