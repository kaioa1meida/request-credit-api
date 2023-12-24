package org.starcode.request.credit.system.service.implementation

import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service
import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.exceptions.BusinessException
import org.starcode.request.credit.system.repository.CreditRepository
import org.starcode.request.credit.system.service.ICreditService
import java.time.LocalDate
import java.util.*


@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {

    override fun saveCredit(credit: Credit): Credit {
        this.validDayFirstInstallment(credit.dayOfFirstInstallment)
        credit.apply {
            customer = customerService.findCustomer(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllCreditsByCustomerId(customerId: Long): List<Credit> {
        return this.creditRepository.findByCustomerId(customerId)
    }

    override fun findCreditByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode)
            ?: throw BusinessException("Creditcode $creditCode not found"))
        return if (credit.customer?.id == customerId) credit else throw IllegalArgumentException("Contact admin")
    }

    private fun validDayFirstInstallment(dayFirstInstallment: LocalDate): Boolean {
        if (dayFirstInstallment < LocalDate.now().plusMonths(3)) {
            return true
            } else throw BusinessException("Invalid Date! Only dates after 3 months of the current date are allowed")
    }
}