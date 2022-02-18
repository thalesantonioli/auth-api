package com.fiap.hmv.publisher

import com.amazonaws.services.sqs.AmazonSQSAsync
import org.springframework.stereotype.Component

@Component
class SQSPublisher(private val publisher: AmazonSQSAsync) {

    fun publish(queue: String, message: String) {
        publisher.sendMessageAsync(queue, message)
    }
}