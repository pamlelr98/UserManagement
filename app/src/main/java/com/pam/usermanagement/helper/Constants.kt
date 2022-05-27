package com.pam.usermanagement.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
private val cipher: String =
    String(Base64.getDecoder().decode("Z2hwX1AzalBJaURSRks2VHVWNGZlaDVoM1c5MlNJSVJhSTBRTVU0Qg=="))

var TOKEN = "bearer $cipher"

const val BASE_URL = "https://api.github.com"