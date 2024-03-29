package com.shoppinglist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.shoppinglist.R
import com.shoppinglist.databinding.ItemShopDisabledBinding
import com.shoppinglist.databinding.ItemShopEnabledBinding
import com.shoppinglist.domain.model.ShopItem
import com.shoppinglist.presentation.diffcallback.ShopItemDiffCallback

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")

        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = viewHolder.binding
        with(binding) {
            root.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            root.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)

            }
            when(binding){
                is ItemShopDisabledBinding -> {
                    binding.shopItem = shopItem
                }
                is ItemShopEnabledBinding -> {
                    binding.shopItem = shopItem
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = -1
        const val MAX_POOL_SIZE = 15
    }
}
