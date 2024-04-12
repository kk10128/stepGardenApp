package edu.uark.virtualfitnessgarden.Model

class ShopItem (
    val imageResource: Int,
    val price: Int,
    var plant_id: Int = -1,
    var isPlant: Boolean = false,
    var isFertilizer: Boolean = false
)