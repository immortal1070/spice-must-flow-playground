package io.immortal.spicemustflow.infrastructure.common.persistence

import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root
import kotlin.reflect.KProperty1

fun <T, V> Root<T>.get(prop: KProperty1<T, V>): Path<V> = this.get(prop.name)