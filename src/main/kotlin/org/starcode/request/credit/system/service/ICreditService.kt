package org.starcode.request.credit.system.service

import org.starcode.request.credit.system.entities.Credit
import java.util.UUID

interface ICreditService {

    fun saveCredit(credit: Credit): Credit

    fun findAllCreditsByCustomerId(customerId: Long): List<Credit>

    fun findCreditByCreditCode(customerId: Long, creditCode: UUID): Credit

}