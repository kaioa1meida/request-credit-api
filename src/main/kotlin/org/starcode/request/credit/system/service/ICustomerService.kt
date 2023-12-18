package org.starcode.request.credit.system.service

import org.starcode.request.credit.system.entities.Customer

interface ICustomerService {

    fun saveCustomer(customer: Customer): Customer

    fun findCustomer(id: Long): Customer?

    fun deleteCustomer(id: Long)

}