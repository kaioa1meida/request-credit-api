package org.starcode.request.credit.system.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.starcode.request.credit.system.entities.Customer

@Repository
interface CustomerRepository:JpaRepository<Customer, Long>