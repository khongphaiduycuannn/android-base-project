package com.ndmquan.base_project.base.activity

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ndmquan.base_project.base.BaseViewModel
import com.ndmquan.base_project.utils.LocalStorageUtils

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding as VB


    protected open val viewModel: BaseViewModel by viewModels()


    protected open val prefs by lazy { LocalStorageUtils.defaultPrefs(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        onActivityCreated()
        initData()
        initViews()
        handleEvents()
        observeData()
    }

    override fun onDestroy() {
        _binding = null
        onActivityDestroyed()
        super.onDestroy()
    }

    /* Hide keyboard when touch outside edittext */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


    protected open fun initData() {}
    protected open fun initViews() {}
    protected open fun handleEvents() {}
    protected open fun observeData() {}
    protected open fun onActivityCreated() {}
    protected open fun onActivityDestroyed() {}
}