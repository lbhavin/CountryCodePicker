package com.ak.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.ak.countrypicker.databinding.FragmentCountryPickerBinding
import com.ak.adapter.CountryListAdapter
import com.ak.countrypicker.R
import com.ak.model.CountryItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomCountryPickerFragment : BottomSheetDialogFragment(),
    CountryListAdapter.OnItemClickListener {
    private var binding: FragmentCountryPickerBinding? = null
    private var listener: OnClickItemListener? = null
    private var countryList = ArrayList<CountryItem>()
    private var countryListAdapter: CountryListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryPickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = BottomSheetDialog(requireContext(), theme)
//        dialog.setOnShowListener {
//
//            val bottomSheetDialog = it as BottomSheetDialog
//            val parentLayout =
//                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//            parentLayout?.let { it ->
//                val behaviour = BottomSheetBehavior.from(it)
////                setupFullHeight(it)
//                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
//            }
//        }
//        return dialog
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        countryListAdapter = CountryListAdapter(this)
        binding?.rvCountryList?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryListAdapter
        }
        countryListAdapter?.newList(countryList)
        countryListAdapter?.notifyDataSetChanged()
        binding?.etSearch?.doOnTextChanged { text, _, _, _ ->
            countryListAdapter?.filter?.filter(text)
        }
    }

    fun setClickListener(listener: OnClickItemListener, list: List<CountryItem>) {
        this.listener = listener
        this.countryList = list as ArrayList<CountryItem>
    }

    interface OnClickItemListener {
        fun onCountryItemClick(item: CountryItem?)
    }

    override fun onSelectItem(item: CountryItem) {
        listener?.onCountryItemClick(item)
    }
}