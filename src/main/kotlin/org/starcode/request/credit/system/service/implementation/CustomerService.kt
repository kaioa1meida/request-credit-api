package org.starcode.request.credit.system.service.implementation

import org.springframework.stereotype.Service
import org.starcode.request.credit.system.entities.Customer
import org.starcode.request.credit.system.repository.CustomerRepository
import org.starcode.request.credit.system.service.ICustomerService

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun saveCustomer(customer: Customer): Customer {
        return this.customerRepository.save(customer)
    }

    override fun findCustomer(id: Long): Customer? {
        return this.customerRepository.findById(id).orElseThrow{throw RuntimeException("Customer ID: $id not found")}
    }

    override fun deleteCustomer(id: Long) {
        return this.customerRepository.deleteById(id)
    }
}