package com.kaus.wordsearch.features.puzzle

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.puzzle.adapter.ArrayLetterGridDataAdapter
import com.kaus.wordsearch.features.puzzle.adapter.WordsAdapter
import com.kaus.wordsearch.model.UsedWord
import com.kaus.wordsearch.utilities.gameplay.StreakLineMapper
import com.kaus.wordsearch.utilities.hide
import com.kaus.wordsearch.utilities.show
import com.kaus.wordsearch.utilities.widgets.LetterBoard
import com.kaus.wordsearch.utilities.widgets.StreakView
import kotlinx.android.synthetic.main.puzzle_fragment.*


@Suppress("DEPRECATION")
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

            val circularProgressDrawable = CircularProgressDrawable(requireContext())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            gameData?.image?.let { img ->
                Glide.with(requireContext())
                    .load(img)
                    .placeholder(circularProgressDrawable)
                    .error(R.color.md_grey_500)
                    .into(puzzle_word_image)
            }
        })

        viewModel.answeredWordLiveData.observe(viewLifecycleOwner, { word ->
            word?.let { mWordsAdapter?.highLightWord(it) } ?: kotlin.run {
                puzzle_letter_board.popStreakLine()
            }
        })

        viewModel.isGameOverLiveData.observe(viewLifecycleOwner, {
            val action =
                PuzzleFragmentDirections.actionPuzzleFragmentToCompletedFragment(args.puzzleId)
            findNavController().navigate(action)
        })

        viewModel.wordDetailsLiveData.observe(viewLifecycleOwner, {
            puzzle_word_title.text = it.word
            puzzle_word_description.text = it.description
            puzzle_word_details.show()
            puzzle_shadow.show()
        })
    }

    private fun clickListeners() {
        puzzle_back.setOnClickListener {
            findNavController().popBackStack(R.id.completedFragment, false)
            findNavController().popBackStack(R.id.puzzleFragment, false)
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
                viewModel.answerWord(
                    str,
                    STREAK_LINE_MAPPER.revMap(streakLine),
                    true,
                    args.puzzleId
                )
            }
        }
        )

        puzzle_shadow.setOnClickListener {
            puzzle_word_details.hide(true)
            puzzle_shadow.hide(true)
        }

        puzzle_word_details.setOnClickListener {

        }

        puzzle_word_share.setOnClickListener {
            puzzle_word_share.hide(true)
            val b = Bitmap.createBitmap(
                puzzle_word_details.width,
                puzzle_word_details.height,
                Bitmap.Config.ARGB_8888
            )
            val c = Canvas(b)
            puzzle_word_details.draw(c)

            val pathofBmp: String =
                MediaStore.Images.Media.insertImage(
                    requireActivity().contentResolver,
                    b,
                    "title",
                    null
                )
            val bmpUri: Uri = Uri.parse(pathofBmp)


            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "image/*"
            waIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
            waIntent.putExtra(Intent.EXTRA_TEXT, "Test")
            requireContext().startActivity(Intent.createChooser(waIntent, "Share with"))

            puzzle_word_share.show()
        }
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
            mWordsAdapter = WordsAdapter(usedWords) {
                viewModel.getWordDataFromDb(args.puzzleId, it.word.word)
            }
            puzzle_word_recycler.layoutManager = GridLayoutManager(requireContext(), 3)
            puzzle_word_recycler.adapter = mWordsAdapter
        }
    }

}