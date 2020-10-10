package com.kaus.wordsearch.features.home

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.MainActivity
import com.kaus.wordsearch.utilities.base_classes.BaseFragment
import com.kaus.wordsearch.utilities.changeBackgroundDrawable
import com.kaus.wordsearch.utilities.changeTextColor
import com.kaus.wordsearch.utilities.hide
import com.kaus.wordsearch.utilities.preferences.DEFAULT_LANGUAGE
import com.kaus.wordsearch.utilities.preferences.Prefs
import com.kaus.wordsearch.utilities.show
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment() {

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

        clickListeners()
        setWidthOfGods()
        updateSettings()

    }

    private fun clickListeners() {
        home_settings.setOnClickListener {
            home_settings_layout.show()
            home_settings_gradient.show()
        }

        home_settings_gradient.setOnClickListener { home_close_setting.performClick() }

        home_close_setting.setOnClickListener {
            home_settings_layout.hide(true)
            home_settings_gradient.hide(true)
        }

        home_hindi_text.setOnClickListener {
            updateLanguage("hi")
            context?.let { ctx ->
                home_hindi_text.changeBackgroundDrawable(
                    ctx, R.drawable.rounded_r5_blue700_bg_black_border
                )
                home_hindi_text.changeTextColor(ctx, R.color.md_white_1000)
                home_english_text.changeBackgroundDrawable(
                    ctx,
                    R.drawable.rounded_r5_white_bg_black_border
                )
                home_english_text.changeTextColor(ctx, R.color.md_black_1000)
            }
        }

        home_english_text.setOnClickListener {
            updateLanguage("en")
            context?.let { ctx ->
                home_english_text.changeBackgroundDrawable(
                    ctx,
                    R.drawable.rounded_r5_blue700_bg_black_border
                )
                home_english_text.changeTextColor(ctx, R.color.md_white_1000)
                home_hindi_text.changeBackgroundDrawable(
                    ctx,
                    R.drawable.rounded_r5_white_bg_black_border
                )
                home_hindi_text.changeTextColor(ctx, R.color.md_black_1000)
            }
        }

        home_play_layout.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLevelsFragment()
            it.findNavController().navigate(action)
        }
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

        home_god_blue.layoutParams.width = width
        home_god_blue.requestLayout()

        home_god_yellow.layoutParams.width = width
        home_god_yellow.requestLayout()

        home_god_pink.layoutParams.width = width
        home_god_pink.requestLayout()

        home_god_green.layoutParams.width = width
        home_god_green.requestLayout()
    }

    private fun updateSettings() {
        when (Prefs.getString(DEFAULT_LANGUAGE, "en")) {
            "en" -> {
                context?.let { ctx ->
                    home_english_text.changeBackgroundDrawable(
                        ctx,
                        R.drawable.rounded_r5_blue700_bg_black_border
                    )
                    home_english_text.changeTextColor(ctx, R.color.md_white_1000)
                    home_hindi_text.changeBackgroundDrawable(
                        ctx,
                        R.drawable.rounded_r5_white_bg_black_border
                    )
                    home_hindi_text.changeTextColor(ctx, R.color.md_black_1000)
                }
            }
            "hi" -> {
                context?.let { ctx ->
                    home_hindi_text.changeBackgroundDrawable(
                        ctx, R.drawable.rounded_r5_blue700_bg_black_border
                    )
                    home_hindi_text.changeTextColor(ctx, R.color.md_white_1000)
                    home_english_text.changeBackgroundDrawable(
                        ctx,
                        R.drawable.rounded_r5_white_bg_black_border
                    )
                    home_english_text.changeTextColor(ctx, R.color.md_black_1000)
                }
            }
            else -> {
                context?.let { ctx ->
                    home_english_text.changeBackgroundDrawable(
                        ctx,
                        R.drawable.rounded_r5_blue700_bg_black_border
                    )
                    home_english_text.changeTextColor(ctx, R.color.md_white_1000)
                    home_hindi_text.changeBackgroundDrawable(
                        ctx,
                        R.drawable.rounded_r5_white_bg_black_border
                    )
                    home_hindi_text.changeTextColor(ctx, R.color.md_black_1000)
                }

            }
        }
    }

    private fun updateLanguage(language: String) {
        Prefs.putString(DEFAULT_LANGUAGE, language)
        home_close_setting.performClick()
        activity?.finishAffinity()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}