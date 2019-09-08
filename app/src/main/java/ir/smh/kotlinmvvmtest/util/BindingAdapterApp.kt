package ir.smh.kotlinmvvmtest.util

import android.widget.EditText

import androidx.databinding.BindingAdapter

import com.google.android.material.textfield.TextInputLayout
import com.ppp_smh.initlibrary.ui.CurrencyFormatTextWatcher

object BindingAdapterApp {

    @BindingAdapter("currencyWatcher")
    fun bindCurrencyWatcher(view: EditText, currencyWatcher: Boolean) {
        view.addTextChangedListener(CurrencyFormatTextWatcher(view))
    }

    /*   @BindingAdapter("imgLoad")
    public static void bindImageLoad(ImageView imageView, ImageModel image) {
        if (image != null) {
            if (image.getStatus().equals(ImgStatus.INSERTR) || image.getStatus().equals(ImgStatus.UPDATE)) // New Image
                imageView.setImageBitmap(image.getBitmap());
            else if (image.getStatus().equals(ImgStatus.DELETE)) {
                imageView.setImageResource(R.drawable.ic_user);
                imageView.setPadding(33, 33, 33, 33);
            } else
                Picasso.get()
                        .load(BuildConfig.BASE_URL_IMG + image.getImage())
                        .placeholder(imageView.getContext().getResources().getDrawable(R.drawable.ic_user))
                        .into(imageView);
        }
    }*/

    /*@BindingAdapter("filterCategory")
    public static void setCargoList(RecyclerView recyclerView,
                                   List<CategoryModel> categorys) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        //check if adapter object isn't null and is from PersonAdapter
        if (adapter != null && adapter instanceof CategoryAdapter)
            //set retrieve array in Adapter arrayList
            ((CategoryAdapter) adapter).setCategorys(categorys);
        else
            throw new IllegalStateException("RecyclerView filterCategory" +
                    " has no adapter set or the "
                    + "adapter isn't of type CategoryAdapter");
    }*/

    @BindingAdapter(value = ["errorTextInput", "onLostFocusValidate"], requireAll = false)
    fun setErrorMessage(view: TextInputLayout, errorMessage: String, validate: Boolean?) {
        view.error = errorMessage
        view.isErrorEnabled = (!validate!!)!!
    }

}
