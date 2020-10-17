package com.kaus.wordsearch.features.puzzle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kaus.wordsearch.R
import com.kaus.wordsearch.model.UsedWord
import kotlinx.android.synthetic.main.row_words.view.*

class WordsAdapter(private val items: List<UsedWord>, val showWordDetails: (UsedWord) -> Unit) :
    RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wordText = view.word!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_words, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordData = items[position]
        holder.wordText.text = wordData.word.word
        if (wordData.isAnswered) {
            holder.wordText.background =
                ContextCompat.getDrawable(context, R.drawable.rounded_r5_blue700_bg)
            holder.wordText.setTextColor(ContextCompat.getColor(context, R.color.md_white_1000))
        } else {
            holder.wordText.background = null
            holder.wordText.setTextColor(ContextCompat.getColor(context, R.color.md_blue_700))
        }

        holder.wordText.setOnClickListener {
            if (wordData.isAnswered) {
                showWordDetails(wordData)
            }
        }
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

    fun highLightWord(correctWord: UsedWord) {
        items.forEachIndexed { index, usedWord ->
            if (usedWord.word.id == correctWord.word.id) {
                notifyItemChanged(index, correctWord)
            }
        }
    }
}

