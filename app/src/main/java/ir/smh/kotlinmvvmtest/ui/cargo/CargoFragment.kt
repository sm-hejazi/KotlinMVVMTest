package ir.smh.kotlinmvvmtest.ui.cargo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ppp_smh.initlibrary.ui.base.BaseActivity
import com.ppp_smh.initlibrary.ui.base.BaseFragment
import ir.smh.kotlinmvvmtest.R
import ir.smh.kotlinmvvmtest.data.model.cargo.CargoModel
import ir.smh.kotlinmvvmtest.databinding.FragmentCargoListBinding
import ir.smh.kotlinmvvmtest.ui.cargo.list.CargoAdapter
import java.util.*
import javax.inject.Inject

//ToDo Detail
//import ir.smh.kotlinmvvmtest.ui.shopproduct.ShopProductActivity;

class CargoFragment : BaseFragment<CargoVM>() {
    private var binding: FragmentCargoListBinding? = null
    private var cargoAdapter: CargoAdapter? = null

    @Inject
    internal var mViewModel: CargoVM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cargo_list, container, false)
        binding!!.viewModel = mViewModel
        (activity as BaseActivity<*>).setBaseVM(mViewModel)
        cargoAdapter = CargoAdapter(mViewModel!!)
        binding!!.products.layoutManager = LinearLayoutManager(activity)
        binding!!.products.adapter = cargoAdapter
        mViewModel!!.getProducts(0)
        return binding!!.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    private fun refreshList(cargoModel: CargoModel, state: String) {
        cargoModel.postageDate = cargoModel.postageDate!!
        val cargoModels = ArrayList<CargoModel>()
        cargoModels.add(cargoModel)
        mViewModel!!.setProductModels(cargoModels)
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel!!.setShowActionSearch(false)//TODO temp
        mViewModel!!.setShowActonFilter(false)

        activity!!.title = getString(R.string.title_all)
    }

    override fun getViewModel(): CargoVM? {
        return mViewModel
    }

    companion object {

        fun newInstance(): CargoFragment {
            val args = Bundle()
            val fragment = CargoFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
