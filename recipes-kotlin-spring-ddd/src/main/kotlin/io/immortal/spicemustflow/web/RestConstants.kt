package io.immortal.spicemustflow.web

const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"
const val ID_PARAM = "id"
const val UUID_PATH = "/{$ID_PARAM:$UUID_REGEX}"