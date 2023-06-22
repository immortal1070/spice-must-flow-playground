package io.immortal.spicemustflow.common.utils

import java.time.OffsetDateTime
import java.time.ZoneOffset

fun nowOffsetUtc(): OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)