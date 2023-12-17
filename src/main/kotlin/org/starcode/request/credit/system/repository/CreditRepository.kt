package org.starcode.request.credit.system.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.starcode.request.credit.system.entities.Credit
import java.util.*

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {

    fun findByCustomerID(customerId: Long): List<Credit>

    fun findByCreditCode(creditCode: UUID): Credit?
}