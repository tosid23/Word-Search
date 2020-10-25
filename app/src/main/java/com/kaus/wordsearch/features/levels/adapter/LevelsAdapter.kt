package com.kaus.wordsearch.features.levels.adapter

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
import com.kaus.wordsearch.features.levels.LevelsFragmentDirections
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.utilities.hide
import com.kaus.wordsearch.utilities.show
import kotlinx.android.synthetic.main.row_levels.view.*


class LevelsAdapter(private val items: List<PuzzleData>, private val width: Int) :
    RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val levelText = view.levels_adapter_level_text!!
        val levelImage = view.levels_adapter_image!!
        val levelTitle = view.levels_adapter_title_text!!
        val levelCompleted = view.levels_adapter_completed_image!!
        val layout = view.levels_adapter_layout!!
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
            append(context.getString(R.string.level))
            append(" ${levelData.level}")
        }
        holder.levelText.text = level

        holder.levelTitle.text = levelData.title

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
            holder.levelCompleted.show()
        } else {
            holder.levelCompleted.hide(true)
        }

        holder.levelImage.layoutParams.width = width
        holder.levelImage.requestLayout()

        holder.layout.setOnClickListener {
            val action =
                LevelsFragmentDirections.actionLevelsFragmentToPuzzleFragment(items[position].id)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

}