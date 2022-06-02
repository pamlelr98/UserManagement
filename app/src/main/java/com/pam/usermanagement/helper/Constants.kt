package com.pam.usermanagement.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*


const val BASE_URL = "https://api.github.com"

@RequiresApi(Build.VERSION_CODES.O)
val cipher: String =
    String(Base64.getDecoder().decode("Z2hwX1AzalBJaURSRks2VHVWNGZlaDVoM1c5MlNJSVJhSTBRTVU0Qg=="))

@RequiresApi(Build.VERSION_CODES.O)
var TOKEN = "bearer $cipher"
