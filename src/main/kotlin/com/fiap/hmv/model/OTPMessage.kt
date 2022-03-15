package com.fiap.hmv.model

data class OTPMessage(val message: String, val subject: String, val toEmails: List<String>)