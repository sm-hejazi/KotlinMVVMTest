package ir.smh.kotlinmvvmtest.data.model.cargo

import com.google.gson.annotations.SerializedName
import com.ppp_smh.initlibrary.util.ParcelableEntity

class CargoModel(
    var id: Int?,
    var title: String?,
    var postageDate: String?,
    var from: String?,
    var to: String?,
    var weight: Int?,
    var price: Long?,
    var cargoType: String?,
    var packingType: String?
) : ParcelableEntity() {

    class Builder {
        @SerializedName("Id")
        var id: Int? = null
        @SerializedName("title")
        var title: String? = null
        @SerializedName("PostageDate")
        var postageDate: String? = null
        @SerializedName("from")
        var from: String? = null
        @SerializedName("to")
        var to: String? = null
        @SerializedName("Weight")
        var weight: Int? = null
        @SerializedName("price")
        var price: Long? = null
        @SerializedName("cargoType")
        var cargoType: String? = null
        @SerializedName("packingType")
        var packingType: String? = null

        fun id(id: Int) = apply { this.id = id }
        fun title(title: String) = apply { this.title = title }
        fun postageDate(postageDate: String) = apply { this.postageDate = postageDate }
        fun from(from: String) = apply { this.from = from }
        fun to(to: String) = apply { this.to = to }
        fun weight(weight: Int) = apply { this.weight = weight }
        fun price(price: Long) = apply { this.price = price }
        fun cargoType(cargoType: String) = apply { this.cargoType = cargoType }
        fun packingType(packingType: String) = apply { this.packingType = packingType }
        fun build() = CargoModel(id, title, postageDate, from, to, weight, price, cargoType, packingType)
    }

}
