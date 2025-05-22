package com.ndmquan.base_project.utils.extension

import android.os.Build

val currentSdk get() = Build.VERSION.SDK_INT

val isSdk29OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

val isSdk30OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

val isSdk31OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

val isSdk33OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

val isSdk35OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE