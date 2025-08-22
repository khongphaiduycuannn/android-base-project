package com.duycuannn.base_project.components

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.duycuannn.base_project.utils.LanguageUtils
import com.duycuannn.base_project.utils.logging.Logging
import kotlinx.coroutines.launch
import java.util.Locale

open class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding as VB


    protected lateinit var languageUtils: LanguageUtils


    protected open val recreateWhenLocaleChanged: Boolean = false

    protected open val hideKeyboardOnOutsideTouch = false

    protected open val shouldShowNavigationBars = false
    protected open val shouldShowStatusBar = true
    protected open val resizeLayoutWhenKeyboardShown = true


    override fun attachBaseContext(base: Context) {
        languageUtils = LanguageUtils(base)
        super.attachBaseContext(updateResourcesLocale(base, languageUtils.currentLocale))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        setUpSystemBars()

        observeLocaleChangedEvent()

        onActivityCreated()
        initData()
        initViews()
        handleEvents()
        observeData()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
        onActivityDestroyed()
    }


    private fun setUpSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val controller = WindowInsetsControllerCompat(window, view)
            var paddingBottom = 0
            var paddingTop = 0

            if (resizeLayoutWhenKeyboardShown) {
                val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
                paddingBottom += imeInsets.bottom
            }

            if (shouldShowNavigationBars) {
                val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
                paddingBottom += navigationBarInsets.bottom
            } else {
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            if (shouldShowStatusBar) {
                val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                paddingTop += statusBarInsets.top
            } else {
                controller.hide(WindowInsetsCompat.Type.statusBars())
                controller.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            view.updatePadding(top = paddingTop, bottom = paddingBottom)
            insets
        }
    }

    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (!hideKeyboardOnOutsideTouch) return super.dispatchTouchEvent(event)

        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun observeLocaleChangedEvent() {
        if (!recreateWhenLocaleChanged) return
        lifecycleScope.launch {
            LanguageUtils.languageChangedEvent.collect {
                Logging.d("Activity recreated cause by locale changed")
                recreate()
            }
        }
    }


    protected open fun onActivityCreated() {}
    protected open fun initData() {}
    protected open fun initViews() {}
    protected open fun handleEvents() {}
    protected open fun observeData() {}
    protected open fun onActivityDestroyed() {}
}