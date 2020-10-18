package com.kaus.wordsearch.features.completed.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.completed.CompletedFragmentDirections
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.utilities.changeBackgroundDrawable
import com.kaus.wordsearch.utilities.changeTextColor
import kotlinx.android.synthetic.main.row_completed_levels.view.*
import kotlinx.android.synthetic.main.row_levels.view.levels_adapter_layout

class CompletedAdapter(private val items: List<PuzzleData>) :
    RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val levelText = view.completed_levels_adapter_level_text!!
        val levelImage = view.completed_levels_adapter_image!!
        val levelTitle = view.completed_levels_adapter_title_text!!
        val levelSubTitle = view.completed_levels_adapter_sub_title_text!!
        val levelDecisionText = view.completed_levels_adapter_decision_text!!
        val layout = view.levels_adapter_layout!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_completed_levels, parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val levelData = items[position]

        val level = buildSpannedString {
            append("Level ${levelData.level}")
        }
        holder.levelText.text = level

        holder.levelTitle.text = "Ramayana"
        holder.levelSubTitle.text = levelData.title

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(context)
            .load(levelData.image)
            .placeholder(circularProgressDrawable)
            .error(R.color.md_grey_500)
            .into(holder.levelImage)

        if (levelData.is_completed) {
            holder.levelDecisionText.text = "Completed!"
            holder.levelDecisionText.changeTextColor(context, R.color.md_green_500)
            holder.levelDecisionText.background = null
        } else {
            holder.levelDecisionText.text = "Start"
            holder.levelDecisionText.changeTextColor(context, R.color.md_white_1000)
            holder.levelDecisionText.changeBackgroundDrawable(
                context,
                R.drawable.rounded_r5_green_500_bg
            )
        }

        holder.layout.setOnClickListener {
            if (!levelData.is_completed) {
                val action =
                    CompletedFragmentDirections.actionCompletedFragmentToPuzzleFragment(items[position].id)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

}