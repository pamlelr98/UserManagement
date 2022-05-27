package com.pam.usermanagement.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
val cipher: String =
    String(Base64.getDecoder().decode("Z2hwX1AzalBJaURSRks2VHVWNGZlaDVoM1c5MlNJSVJhSTBRTVU0Qg=="))
var TOKEN = "bearer $cipher"
