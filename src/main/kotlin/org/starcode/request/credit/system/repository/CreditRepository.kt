package org.starcode.request.credit.system.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.starcode.request.credit.system.entities.Credit

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {
}