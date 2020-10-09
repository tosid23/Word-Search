package com.kaus.wordsearch.features.home

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaus.wordsearch.R
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setWidthOfGods()
    }

    @Suppress("DEPRECATION")
    private fun setWidthOfGods() {
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context?.display?.getRealMetrics(displayMetrics)
        } else {
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        }
        val width = displayMetrics.widthPixels / 4

        god_blue.layoutParams.width = width
        god_blue.requestLayout()

        god_yellow.layoutParams.width = width
        god_yellow.requestLayout()

        god_pink.layoutParams.width = width
        god_pink.requestLayout()

        god_green.layoutParams.width = width
        god_green.requestLayout()
    }

}