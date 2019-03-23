package com.example.maxim.kotlinhw2.common

import java.text.SimpleDateFormat
import java.util.*


fun Date.format(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(this)