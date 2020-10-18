package com.kaus.wordsearch.features.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.completed.adapter.CompletedAdapter
import kotlinx.android.synthetic.main.completed_fragment.*

class CompletedFragment : Fragment() {

    companion object {
        fun newInstance() = CompletedFragment()
    }

    private val args: CompletedFragmentArgs by navArgs()

    private lateinit var viewModel: CompletedViewModel
    private lateinit var adapter: CompletedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.completed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompletedViewModel::class.java)
        clickListeners()
        observers()
        viewModel.getData(args.puzzleId)
    }

    private fun observers() {
        viewModel.puzzleLiveData.observe(viewLifecycleOwner, {
            adapter = CompletedAdapter(it)
            context?.let { ctx ->
                val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                completed_recycler.layoutManager = layoutManager
                completed_recycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun clickListeners() {
        completed_back.setOnClickListener {
            findNavController().popBackStack(R.id.puzzleFragment, false)
            it.findNavController().navigateUp()
        }
    }
}