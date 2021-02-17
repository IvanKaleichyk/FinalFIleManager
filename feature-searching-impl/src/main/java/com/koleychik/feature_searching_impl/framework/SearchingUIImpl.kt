package com.koleychik.feature_searching_impl.framework

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.koleychik.feature_searching_api.SearchingUIApi
import com.koleychik.feature_searching_impl.R

class SearchingUIImpl : SearchingUIApi {


    private var onClose: (() -> Unit)? = null

    private var textWatcher: TextWatcher? = null

    private lateinit var rootView: View

    private var isSearchingOpen = false

    private lateinit var motionLayout: MotionLayout
    private lateinit var fabIcon: FloatingActionButton

    override fun endSetup() {
        val edt = rootView.findViewById<EditText>(R.id.edtSearching)
        fabIcon = rootView.findViewById(R.id.fabSearch)
        motionLayout = rootView.findViewById(R.id.motionLayout)

        edt.addTextChangedListener(textWatcher)
        fabIcon.setOnClickListener(createOnClickToFabIcon())
    }

    private fun createOnClickToFabIcon() = View.OnClickListener {
        isSearchingOpen = !isSearchingOpen
        if (isSearchingOpen) openEdt()
        else closeEdt()
    }

    private fun openEdt() {
        motionLayout.transitionToState(R.id.open)
        fabIcon.setImageResource(R.drawable.search_icon_32_black)
    }

    private fun closeEdt() {
        onClose?.let { close -> close() }
        motionLayout.transitionToState(R.id.close)
        fabIcon.setImageResource(R.drawable.close_icon_32_black)
    }

    override fun getSearchLayoutId(): Int = R.layout.layout_searching

    override fun setOnCloseSearching(onClose: () -> Unit) {
        this.onClose = onClose
    }

    override fun setTextWatcher(textWatcher: TextWatcher) {
        this.textWatcher = textWatcher
    }

    override fun setRootView(rootView: View) {
        this.rootView = rootView
    }

    override fun isShowIconVisible(boolean: Boolean) {
        fabIcon.isVisible = boolean
    }
}