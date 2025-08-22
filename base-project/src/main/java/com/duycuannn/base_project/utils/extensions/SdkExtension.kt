package com.duycuannn.base_project.utils.extensions

import android.os.Build

val currentSdk get() = Build.VERSION.SDK_INT

val isSdk26OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

val isSdk27OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

val isSdk28OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

val isSdk29OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

val isSdk30OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

val isSdk31OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

val isSdk32OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2

val isSdk33OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

val isSdk35OrAbove get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE