package io.immortal.spicemustflow.infrastructure.exception

class ObjectNotFoundException(name: String, id: Any?) : RuntimeException("$name with id=$id not found!")