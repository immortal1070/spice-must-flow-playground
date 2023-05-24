
import java.time.OffsetDateTime
import java.time.ZoneOffset

//TODO check best practives for such utils in kotlin
fun nowOffsetUtc(): OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)