package com.r3d1r4ph.currencyrates.utils.exceptions

import java.io.IOException

class StatusCodeException(override val message: String?) : IOException(message)