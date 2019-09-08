package ir.smh.kotlinmvvmtest.data.model.cargo

import com.google.gson.annotations.SerializedName
import com.ppp_smh.initlibrary.util.ParcelableEntity

class CargoModel(
    @SerializedName("Id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("PostageDate")
    var postageDate: String?,
    @SerializedName("from")
    var from: String?,
    @SerializedName("to")
    var to: String?,
    @SerializedName("Weight")
    var weight: Int?,
    @SerializedName("price")
    var price: Long?,
    @SerializedName("cargoType")
    var cargoType: String?,
    @SerializedName("packingType")
    var packingType: String?
) : ParcelableEntity() {

    data class Builder(
        var id: Int? = null,
        var title: String? = null,
        var postageDate: String? = null,
        var from: String? = null,
        var to: String? = null,
        var weight: Int? = null,
        var price: Long? = null,
        var cargoType: String? = null,
        var packingType: String? = null) {

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
