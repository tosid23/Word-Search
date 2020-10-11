package com.kaus.wordsearch.features.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.puzzle.adapter.ArrayLetterGridDataAdapter
import com.kaus.wordsearch.features.puzzle.adapter.WordsAdapter
import com.kaus.wordsearch.features.puzzle.gameplay.StreakLineMapper
import com.kaus.wordsearch.model.UsedWord
import com.kaus.wordsearch.widgets.LetterBoard
import com.kaus.wordsearch.widgets.StreakView
import kotlinx.android.synthetic.main.puzzle_fragment.*

class PuzzleFragment : Fragment() {

    companion object {
        fun newInstance() = PuzzleFragment()
    }

    private val args: PuzzleFragmentArgs by navArgs()
    private lateinit var viewModel: PuzzleViewModel
    private var mLetterAdapter: ArrayLetterGridDataAdapter? = null
    private var mWordsAdapter: WordsAdapter? = null
    private val STREAK_LINE_MAPPER: StreakLineMapper = StreakLineMapper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.puzzle_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PuzzleViewModel::class.java)
        clickListeners()
        observers()

        viewModel.createGame(args.puzzleId)
    }

    private fun observers() {
        viewModel.gameLiveData.observe(viewLifecycleOwner, { gameData ->
            gameData?.grid?.getArray()?.let {
                showLetterGrid(it)
            }
            gameData?.usedWords?.let {
                showUsedWords(it)
            }
            gameData?.name?.let {
                puzzle_title.text = it
            }
        })

        viewModel.answeredWordLiveData.observe(viewLifecycleOwner, { word ->
            word?.let { mWordsAdapter?.highLightWord(it) } ?: kotlin.run {
                puzzle_letter_board.popStreakLine()
            }
        })
    }

    private fun clickListeners() {
        puzzle_back.setOnClickListener {
            it.findNavController().navigateUp()
        }

        puzzle_letter_board.setOnLetterSelectionListener(object :
            LetterBoard.OnLetterSelectionListener {
            override fun onSelectionBegin(streakLine: StreakView.StreakLine, str: String) {
                streakLine.color = ContextCompat.getColor(requireContext(), R.color.md_cyan_200)
            }

            override fun onSelectionDrag(streakLine: StreakView.StreakLine, str: String) {

            }

            override fun onSelectionEnd(streakLine: StreakView.StreakLine, str: String) {
                viewModel.answerWord(str, STREAK_LINE_MAPPER.revMap(streakLine), true)
            }
        }
        )
    }

    private fun showLetterGrid(grid: Array<CharArray>) {
        if (mLetterAdapter == null) {
            mLetterAdapter = ArrayLetterGridDataAdapter(grid)
            puzzle_letter_board.dataAdapter = mLetterAdapter
        } else {
            mLetterAdapter!!.grid = grid
        }
    }

    private fun showUsedWords(usedWords: List<UsedWord>) {
        if (mWordsAdapter == null) {
            mWordsAdapter = WordsAdapter(usedWords, 0)
            puzzle_word_recycler.layoutManager = GridLayoutManager(requireContext(), 3)
            puzzle_word_recycler.adapter = mWordsAdapter
        }
    }

}