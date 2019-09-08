package ir.smh.kotlinmvvmtest.ui.cargo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.smh.kotlinmvvmtest.R
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.databinding.CargoItemBinding
import ir.smh.kotlinmvvmtest.ui.cargo.CargoVM
import java.util.*

class CargoAdapter(private val mViewModel: CargoVM) :
    RecyclerView.Adapter<CargoAdapter.MyViewHolder>() {

    private var cargoList: MutableList<CargoModel>? = null

    init {
        this.cargoList = ArrayList()
    }

    fun deleteAll() {
        cargoList!!.clear()
        notifyDataSetChanged()
    }

    fun setCargoList(cargoList: MutableList<CargoModel>) {
        this.cargoList = cargoList
        notifyDataSetChanged()
    }

    fun addMoreProducts(products: List<CargoModel>) {
        if (products.isNotEmpty())
            this.cargoList!!.addAll(this.cargoList!!.size, products)
        notifyDataSetChanged()
    }

    fun addNewProduct(products: List<CargoModel>) {
        if (products.isNotEmpty())
            this.cargoList!!.add(0, products[0])
        notifyDataSetChanged()
    }

    fun updateProduct(products: List<CargoModel>) {
        if (products.isNotEmpty())
            this.cargoList!![findPos(products[0].id!!)] = products[0]
        notifyDataSetChanged()
    }

    fun setProduct(products: CargoModel) {
        this.cargoList!!.add(products)
        notifyDataSetChanged()
    }

    fun updateProduct(id: Int, product: CargoModel) {
        val pos = findPos(id)
        this.cargoList!![pos] = product
        notifyDataSetChanged()
    }

    fun deleteProduct(id: Int) {
        val pos = findPos(id)
        this.cargoList!!.removeAt(pos)
        notifyDataSetChanged()
    }

    private fun findPos(id: Int): Int {
        var pos = 0
        for (i in this.cargoList!!.indices) {
            if (this.cargoList!![i].id == id) {
                pos = i
                break
            }
        }
        return pos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayoutBinding = DataBindingUtil
            .inflate<CargoItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.cargo_card, parent, false
            )
        return MyViewHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val cargo = cargoList!![position]

        val cargoItemVM = CargoItemVM(cargo)
        holder.itemLayoutBinding.item.setOnClickListener { mViewModel.detailProduct(cargoList!![position]) }

        holder.setProductItemVM(cargoItemVM)
    }

    override fun getItemCount(): Int {
        return cargoList!!.size
    }

    inner class MyViewHolder internal constructor(internal var itemLayoutBinding: CargoItemBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root) {

        //fill each list item with Observable items(id,name,family)
        internal fun setProductItemVM(cargoItemVM: CargoItemVM) {
            itemLayoutBinding.itemVM = cargoItemVM
            itemLayoutBinding.executePendingBindings()
        }

    }
}
