package com.gildedrose

internal const val sulfurasType = "Sulfuras, Hand of Ragnaros"
const val agedBrieType = "Aged Brie"
const val backstagePassType = "Backstage passes to a TAFKAL80ETC concert"
const val conjuredManaCake = "Conjured Mana Cake"

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            item.quality += calculateQualityChange(item)

            if (item.name != sulfurasType) {
                item.sellIn = item.sellIn - 1
            }

            if (item.sellIn < 0) {
                if (item.name != agedBrieType) {
                    if (item.name != backstagePassType) {
                        if (item.quality > 0) {
                            if (item.name != sulfurasType) {
                                item.quality = item.quality - 1
                            }
                        }
                    } else {
                        item.quality = item.quality - item.quality
                    }
                }
            }
        }
    }

    private fun calculateQualityChange(item: Item) = when (item.name) {
        conjuredManaCake -> conjuredQualityChange()
        sulfurasType -> 0
        agedBrieType -> if (item.quality < 50) 1 else 0
        backstagePassType -> item.backstagePassChange()
        else -> if (item.quality > 0) -1 else 0
    }

    private fun Item.backstagePassChange() = if (sellIn > 10) {
        1
    } else if (sellIn in 6..10) {
        2
    } else if (sellIn in 1..5) {
        3
    } else {
        0
    }

    private fun conjuredQualityChange() = -2

}

