package com.gildedrose

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.async.asyncSetup
import com.zegreatrob.testmints.setup
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun givenNormalItemUpdateQualityWillDecreaseSellInValue() = asyncSetup(object {
        val irrelevant = 20
        val arbitrarySellIn = 10
        val item = Item("Normal", sellIn = arbitrarySellIn, quality = irrelevant)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.sellIn.assertIsEqualTo(arbitrarySellIn - 1)
    }

    @Test
    fun givenNormalItemUpdateQualityWillDecreaseQualityValue() = asyncSetup(object {
        val irrelevant = 30
        val arbitraryQuality = 25
        val item = Item("Normal", sellIn = irrelevant, quality = arbitraryQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality - 1)
    }

    @Test
    fun givenNormalItemQualityDegrades2xAfterSellByDate() = setup(object {
        val arbitraryQuality = 42
        val dayAfterExpiration = 0
        val item = Item("Normal", sellIn = dayAfterExpiration, quality = arbitraryQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality - 2)
    }

    @Test
    fun givenNormalItemQualityCanNeverBeNegative() = setup(object {
        val zeroQuality = 0
        val irrelevant = 42
        val item = Item("Normal", sellIn = irrelevant, quality = zeroQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(zeroQuality)
    }

    @Test
    fun givenAgedBrieQualityIncreases() = setup(object {
        val arbitraryQuality = 42
        val irrelevant = 10
        val agedBrie = Item(agedBrieType, sellIn = irrelevant, quality = arbitraryQuality)
        val gildedRose = GildedRose(listOf(agedBrie))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        agedBrie.quality.assertIsEqualTo(arbitraryQuality + 1)
    }

    @Test
    fun itemCannotHaveMoreThan50Quality() = setup(object {
        val maximumQuality = 50
        val irrelevant = 10
        val itemTypeThatIncreasesWithAge = agedBrieType
        val item = Item(itemTypeThatIncreasesWithAge, sellIn = irrelevant, quality = maximumQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(maximumQuality)
    }

    @Test
    fun legendariesNeverDecreasesInQuality() = setup(object {
        val arbitrarySellIn = 0
        val legendaryQuality = 80
        val legendaryItem = Item(sulfurasType, sellIn = arbitrarySellIn, quality = legendaryQuality)
        val gildedRose = GildedRose(listOf(legendaryItem))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        legendaryItem.quality.assertIsEqualTo(legendaryQuality)
    }

    @Test
    fun legendariesDoNotDecreaseInQualityEvenWhenLessThan80() = setup(object {
        val arbitrarySellIn = 0
        val legendaryQuality = 79
        val legendaryItem = Item(sulfurasType, sellIn = arbitrarySellIn, quality = legendaryQuality)
        val gildedRose = GildedRose(listOf(legendaryItem))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        legendaryItem.quality.assertIsEqualTo(legendaryQuality)
    }

    @Test
    fun moreThanTenDaysBeforeSellInBackstagePassesIncreaseInQualityByOne() = setup(object {
        val moreThanTen = 11
        val arbitraryQuality = 42
        val item = Item(
            name = backstagePassType,
            sellIn = moreThanTen,
            quality = arbitraryQuality,
        )
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality + 1)
    }

    @Test
    fun moreTenDaysBeforeSellInBackstagePassesIncreaseInQualityByTwo() = setup(object {
        val tenDays = 10
        val arbitraryQuality = 42
        val item = Item(
            name = backstagePassType,
            sellIn = tenDays,
            quality = arbitraryQuality,
        )
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality + 2)
    }

    @Test
    fun betweenTenAndSixBeforeSellInBackstagePassesIncreaseInQualityByTwo() = setup(object {
        val moreThanFive = 6
        val arbitraryQuality = 42
        val item = Item(
            name = backstagePassType,
            sellIn = moreThanFive,
            quality = arbitraryQuality,
        )
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality + 2)
    }

    @Test
    fun fiveDaysBeforeSellInBackstagePassesIncreaseInQualityByThree() = setup(object {
        val fiveDays = 5
        val arbitraryQuality = 42
        val item = Item(name = backstagePassType, sellIn = fiveDays, quality = arbitraryQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality + 3)
    }

    @Test
    fun oneDayBeforeSellInBackstagePassesIncreaseInQualityByThree() = setup(object {
        val oneDay = 1
        val arbitraryQuality = 42
        val item = Item(name = backstagePassType, sellIn = oneDay, quality = arbitraryQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality + 3)
    }

    @Test
    fun zeroDaySellInBackstagePassesHaveQualityOfZero() = setup(object {
        val zeroDay = 0
        val arbitraryQuality = 42
        val item = Item(name = backstagePassType, sellIn = zeroDay, quality = arbitraryQuality)
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(0)
    }

    @Test
    fun conjuredItemsDegradeInQualityTwiceAsFastAsNormalItems() = setup(object {
        val irrelevant = 42
        val arbitraryQuality = 25
        val item = Item(name = conjuredManaCake, sellIn = irrelevant, quality = arbitraryQuality )
        val gildedRose = GildedRose(listOf(item))
    }) exercise {
        gildedRose.updateQuality()
    } verify {
        item.quality.assertIsEqualTo(arbitraryQuality - 2)
    }

}


