package io.immortal.spicemustflow.util

import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.reflect.KProperty1

//TODO check best practices for such utils in kotlin
fun nowOffsetUtc(): OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

fun <T, V> Root<T>.get(prop: KProperty1<T, V>): Path<V> = this.get(prop.name)