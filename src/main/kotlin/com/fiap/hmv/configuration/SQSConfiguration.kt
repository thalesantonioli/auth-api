package com.fiap.hmv.configuration

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SQSConfiguration {

    @Value("\${cloud.aws.region:sa-east-1}")
    private val awsRegion: String? = null

    @Bean
    @Primary
    fun amazonSNSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withCredentials(credentialsProvider())
            .withRegion(awsRegion)
            .build()
    }

    private fun credentialsProvider(): AWSCredentialsProvider {
        return DefaultAWSCredentialsProviderChain()
    }
}