package com.example.firsthomeworkmhs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filter_item.*
import kotlinx.android.synthetic.main.filter_item.view.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.skill_item.*
import java.io.Serializable

class FilterFragment : Fragment() {
    private lateinit var skills: ArrayList<Skill>
    private lateinit var parent: FiltersParentActivity
    private var filterDict: MutableMap<String, Boolean> = mutableMapOf()
    private lateinit var checkBoxAll: CheckBox
    private val filterAdapter = ListDelegationAdapter(filtersAdapterDelegate())
    private lateinit var allCheckboxSelected: CompoundButton.OnCheckedChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            skills = it.getSerializable("skills") as ArrayList<Skill>
        }
        for (item in skills) {
            if (!filterDict.containsKey(item.exp)) {
                filterDict[item.exp] = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parent = activity!! as FiltersParentActivity
        closeButton.setOnClickListener {
            parent.filterFragmentResulted(0, null)
        }
        accept.setOnClickListener {
            if (checkAll.isChecked) {
                parent.filterFragmentResulted(0, null)
            }
        }
        allCheckboxSelected = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            for (i in 0 until filterAdapter.itemCount) {
                checkBoxes[i].filterCheck.isChecked = isChecked
            }
        }
        checkBoxAll = checkAll
        checkBoxAll.setOnCheckedChangeListener(allCheckboxSelected)
        filterAdapter.items = filterDict.keys.toList()
        with(checkBoxes) {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun filtersAdapterDelegate() =
        adapterDelegateLayoutContainer<String, String>(R.layout.filter_item) {
            bind {
                filterCheck.text = item
                filterCheck.setOnCheckedChangeListener { _, isChecked ->
                    filterDict[item] = isChecked
                    if (!isChecked) {
                        checkBoxAll.isChecked = false
                    }
                }
            }
        }

    companion object {
        @JvmStatic
        fun newInstance(skills: ArrayList<Skill>) =
            FilterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("skills", skills as Serializable)
                }
            }
    }
}