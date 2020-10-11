package com.kaus.wordsearch.features.levels

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.levels.adapter.LevelsAdapter
import com.kaus.wordsearch.utilities.LANGUAGE_ENGLISH
import kotlinx.android.synthetic.main.levels_fragment.*

class LevelsFragment : Fragment() {

    companion object {
        fun newInstance() = LevelsFragment()
    }

    private lateinit var viewModel: LevelsViewModel
    private lateinit var adapter: LevelsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.levels_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LevelsViewModel::class.java)
        clickListeners()
        observers()
        viewModel.getPuzzleData(LANGUAGE_ENGLISH)
    }

    private fun clickListeners() {
        levels_back.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    @Suppress("DEPRECATION")
    private fun observers() {
        viewModel.puzzleLiveData.observe(viewLifecycleOwner, {
            val displayMetrics = DisplayMetrics()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                context?.display?.getRealMetrics(displayMetrics)
            } else {
                activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            }
            val width = (displayMetrics.widthPixels / 2) - (displayMetrics.widthPixels / 8)

            adapter = LevelsAdapter(it, width)

            val layoutManager = GridLayoutManager(requireContext(), 2)
            levels_recycler.layoutManager = layoutManager
            levels_recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

}