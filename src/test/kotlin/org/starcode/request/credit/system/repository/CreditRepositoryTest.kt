package org.starcode.request.credit.system.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import org.starcode.request.credit.system.entities.Address
import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.entities.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setUp() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer= customer))
    }

    @Test
    fun `should find credit by credit code`() {
        //given
        val creditCode1 = UUID.fromString("aa547c0f-9a6a-451f-8c89-afddce916a29")
        val creditCode2 = UUID.fromString("49f740be-46a7-449b-84e7-ff5b7986d7ef")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2
        //when
        val fakeCredit1 = creditRepository.findByCreditCode(creditCode1)
        val fakeCredit2 = creditRepository.findByCreditCode(creditCode2)
        //then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)

    }

    @Test
    fun `should find credits by customer id`() {
        //given
        val customerId: Long = 1L
        //when
        val fakeCreditList = creditRepository.findByCustomerId(customerId)
        //then
        Assertions.assertThat(fakeCreditList).isNotEmpty
        Assertions.assertThat(fakeCreditList.size).isEqualTo(2)
        Assertions.assertThat(fakeCreditList).contains(credit1, credit2)
    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal(20000),
        dayOfFirstInstallment: LocalDate = LocalDate.of(2024, 2, 23),
        numberOfInstallments: Int = 5,
        customer: Customer,
    ) = Credit (
        creditValue = creditValue,
        dayOfFirstInstallment = dayOfFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer,
    )

    private fun buildCustomer(
        firstName: String = "Kaio",
        lastName: String = "Almeida",
        cpf: String = "28475934625",
        email: String = "kaio@gmail.com",
        password: String = "kazzy1234",
        zipCode: String = "kazzy1234",
        street: String = "Rua do Kaio",
        income: BigDecimal = BigDecimal.valueOf(10000.0),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
    )

}