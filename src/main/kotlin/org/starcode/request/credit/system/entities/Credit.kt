package org.starcode.request.credit.system.entities

import jakarta.persistence.*
import org.starcode.request.credit.system.utils.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
data class Credit(
    @Column(nullable = false, unique = true) val creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false) val creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) val dayOfFirstInstallment: LocalDate,
    @Column(nullable = false)val numberOfInstallments: Int = 0,
    @Enumerated val creditStatus: Status = Status.IN_PROGRESS,
    @ManyToOne val customer: Customer? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
)
