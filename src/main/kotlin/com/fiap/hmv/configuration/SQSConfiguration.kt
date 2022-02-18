package com.fiap.hmv.configuration

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SQSConfiguration {

    @Value("\${cloud.aws.region:eu-central-1}")
    private val awsRegion: String? = null

    @Value("\${cloud.aws.endpoint}")
    private val awsEndpoint: String? = null

    @Bean
    @Primary
    fun amazonSNSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withCredentials(credentialsProvider())
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(awsEndpoint, awsRegion)
            )
            .build()
    }

    private fun credentialsProvider(): AWSCredentialsProvider {
        return DefaultAWSCredentialsProviderChain()
    }
}