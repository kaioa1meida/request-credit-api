package org.starcode.request.credit.system.exceptions

import java.time.LocalDateTime

data class ExceptionDetails(
    val title: String,
    val timestamp: String,
    val status: Int,
    val exception: String,
    val details: MutableMap<String, String?>)
