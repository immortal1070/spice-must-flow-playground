package io.immortal.spicemustflow.common.exception

class ObjectNotFoundException(name: String, id: Any?) : RuntimeException("$name with id=$id not found!")