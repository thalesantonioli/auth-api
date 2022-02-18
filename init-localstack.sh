#!/bin/bash
set -x
awslocal sqs create-queue --queue-name login-otp-notification
set +x