package org.starcode.request.credit.system.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.entities.Customer
import org.starcode.request.credit.system.exceptions.BusinessException
import org.starcode.request.credit.system.repository.CreditRepository
import org.starcode.request.credit.system.service.implementation.CreditService
import org.starcode.request.credit.system.service.implementation.CustomerService
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.collections.List

//@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK lateinit var creditRepository: CreditRepository
    @MockK lateinit var customerService: CustomerService
    @InjectMockKs lateinit var creditService: CreditService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @AfterEach
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `should create credit`() {
        //given
        val fakeCredit: Credit = buildCredit()
        val customerId: Long = 1L

        every { customerService.findCustomer(customerId) } returns fakeCredit.customer!!
        every { creditRepository.save(fakeCredit) } returns fakeCredit
        //when
        val actual: Credit = creditService.saveCredit(fakeCredit)
        //then
        verify(exactly = 1) { customerService.findCustomer(customerId) }
        verify(exactly = 1) { creditRepository.save(fakeCredit) }

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)
    }

    @Test
    fun `should not create credit when invalid day first installment`() {
        //given
        val invalidDayFirstInstallment: LocalDate = LocalDate.now().plusMonths(5)
        val fakeCredit: Credit = buildCredit(dayOfFirstInstallment = invalidDayFirstInstallment)
        every { creditRepository.save(fakeCredit) } answers {fakeCredit}
        //when
        Assertions.assertThatThrownBy { creditService.saveCredit(fakeCredit) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Invalid Date! Only dates after 3 months of the current date are allowed")
        //then
        verify(exactly = 0) { creditRepository.save(any()) }

    }

    @Test
    fun `should return credit for a valid customer and credit code`() {
        // given
        val customerId: Long = 1L
        val credit: Credit = buildCredit(customer = Customer(id = customerId))
        val creditCode = credit.creditCode

        every { creditRepository.findByCreditCode(creditCode) } returns credit
        //when
        val actual = creditService.findCreditByCreditCode(customerId, creditCode)
        //then
        Assertions.assertThat(actual).isNotNull
    }

    @Test
    fun `should throw BusinessException for invalid credit code`() {
        //given
        val customerId: Long = 1L
        val invalidCreditCode: UUID = UUID.randomUUID()

        every { creditRepository.findByCreditCode(invalidCreditCode) } returns null
        //when
        //then
        Assertions.assertThatThrownBy { creditService.findCreditByCreditCode(customerId, invalidCreditCode) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Creditcode $invalidCreditCode not found")
        //then
        verify(exactly = 1) { creditRepository.findByCreditCode(invalidCreditCode) }
    }

    @Test
    fun `should throw IllegalArgumentException for different customer ID`() {
        //given
        val customerId: Long = 1L
        val creditCode: UUID = UUID.randomUUID()
        val credit: Credit = buildCredit(customer = Customer(id = 2L))

        every { creditRepository.findByCreditCode(creditCode) } returns credit
        //when
        //then
        Assertions.assertThatThrownBy { creditService.findCreditByCreditCode(customerId, creditCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Contact admin")

        verify { creditRepository.findByCreditCode(creditCode) }
    }

    @Test
    fun `should find credits list by customer id`() {
        //given
        val customerId: Long = buildCredit().customer?.id!!
        val expectedCredits: List<Credit> = listOf(buildCredit(), buildCredit(), buildCredit())

        every { creditRepository.findByCustomerId(customerId) } returns expectedCredits
        //when
        val actual = creditService.findAllCreditsByCustomerId(customerId)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isNotEmpty
        Assertions.assertThat(actual).isSameAs(expectedCredits)

        verify(exactly = 1) { creditRepository.findByCustomerId(customerId) }
    }

    companion object {
        fun buildCredit(
            creditValue: BigDecimal = BigDecimal(20000),
            dayOfFirstInstallment: LocalDate = LocalDate.of(2024, 2, 23),
            numberOfInstallments: Int = 5,
            customer: Customer = CustomerServiceTest.buildCustomer()
        ) = Credit (
            creditValue = creditValue,
            dayOfFirstInstallment = dayOfFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            customer = customer
        )
    }

}
