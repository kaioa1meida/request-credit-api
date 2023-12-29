package org.starcode.request.credit.system.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.starcode.request.credit.system.dto.request.CreditDto
import org.starcode.request.credit.system.dto.response.CreditView
import org.starcode.request.credit.system.dto.response.CreditViewList
import org.starcode.request.credit.system.entities.Credit
import org.starcode.request.credit.system.service.implementation.CreditService
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credit/")
class CreditController(
    private val creditService: CreditService
) {

    @PostMapping
    fun saveCredit(@RequestBody @Valid creditDto: CreditDto): ResponseEntity<String> {
        val savedCredit: Credit = this.creditService.saveCredit(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${savedCredit.creditCode}, by customer: ${savedCredit.customer?.firstName} created!")
    }

    @GetMapping
    fun findCreditByCustomer(
        @RequestParam(value = "customerId") customerId: Long
    ): ResponseEntity<List<CreditViewList>> {
        val creditViewList: List<CreditViewList> = this.creditService.findAllCreditsByCustomerId(customerId)
            .stream()
            .map { credit: Credit -> CreditViewList(credit) }
            .collect(Collectors.toList())

        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)
    }

    @GetMapping("{creditCode}")
    fun findCreditByCreditCode(
        @PathVariable creditCode: UUID,
        @RequestParam(value = "customerId") customerId: Long
    ): ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.findCreditByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))
    }
}