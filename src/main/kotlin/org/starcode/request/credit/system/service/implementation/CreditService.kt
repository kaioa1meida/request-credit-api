package org.starcode.request.credit.system.service.implementation

import org.springframework.stereotype.Service
import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.repository.CreditRepository
import org.starcode.request.credit.system.service.ICreditService
import java.util.*


@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {

    override fun saveCredit(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findCustomer(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllCreditsByCustomerId(customerId: Long): List<Credit> {
        return this.creditRepository.findByCustomerID(customerId)
    }

    override fun findCreditByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode)
            ?: throw RuntimeException("Creditcode $creditCode not found"))
        return if (credit.customer?.id == customerId) credit else throw RuntimeException("Contact admin")
    }
}