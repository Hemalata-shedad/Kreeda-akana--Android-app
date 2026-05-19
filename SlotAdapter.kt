package com.kreedaankana.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kreedaankana.R
import com.kreedaankana.data.model.SlotBooking
import com.kreedaankana.databinding.ItemSlotBinding
import com.kreedaankana.utils.Constants

data class SlotUiModel(
    val slotIndex: Int,
    val timeLabel: String,
    val booking: SlotBooking?       // null = FREE
)

class SlotAdapter(
    private val onBook: (Int) -> Unit
) : ListAdapter<SlotUiModel, SlotAdapter.SlotViewHolder>(DIFF) {

    inner class SlotViewHolder(private val b: ItemSlotBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: SlotUiModel) {
            b.tvTime.text = item.timeLabel
            if (item.booking == null) {
                b.tvStatus.text = "FREE"
                b.tvStatus.setTextColor(b.root.context.getColor(R.color.green_free))
                b.cardSlot.setCardBackgroundColor(b.root.context.getColor(R.color.bg_free))
                b.tvTeam.text = ""
                b.btnBook.isEnabled = true
                b.btnBook.text = "Book"
                b.btnBook.setOnClickListener { onBook(item.slotIndex) }
            } else {
                b.tvStatus.text = "BOOKED"
                b.tvStatus.setTextColor(b.root.context.getColor(R.color.red_booked))
                b.cardSlot.setCardBackgroundColor(b.root.context.getColor(R.color.bg_booked))
                b.tvTeam.text = "🏟 ${item.booking.teamName} · ${item.booking.sport}"
                b.btnBook.isEnabled = false
                b.btnBook.text = "Taken"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder =
        SlotViewHolder(
            ItemSlotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<SlotUiModel>() {
            override fun areItemsTheSame(a: SlotUiModel, b: SlotUiModel) =
                a.slotIndex == b.slotIndex

            override fun areContentsTheSame(a: SlotUiModel, b: SlotUiModel) = a == b
        }
    }
}
