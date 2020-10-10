package com.kaus.wordsearch.features.levels.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.kaus.wordsearch.R
import com.kaus.wordsearch.features.levels.model.LevelsData
import com.kaus.wordsearch.utilities.hide
import com.kaus.wordsearch.utilities.show
import kotlinx.android.synthetic.main.row_levels.view.*

class LevelsAdapter(private val items: ArrayList<LevelsData>, private val width: Int) :
    RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val levelText = view.levels_adapter_level_text!!
        val levelImage = view.levels_adapter_image!!
        val levelTitle = view.levels_adapter_title_text!!
        val levelCompleted = view.levels_adapter_completed_image!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_levels, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val levelData = items[position]

        val level = buildSpannedString {
            append("Level ${levelData.level}")
        }
        holder.levelText.text = level

        holder.levelTitle.text = levelData.name
        holder.levelImage.setImageDrawable(ContextCompat.getDrawable(context, levelData.image))

        if (levelData.is_completed) {
            holder.levelCompleted.show()
        } else {
            holder.levelCompleted.hide(true)
        }

        holder.levelImage.layoutParams.width = width
        holder.levelImage.requestLayout()
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

}