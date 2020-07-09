package com.example.firsthomeworkmhs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.core.view.forEach
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

/*
Многоходовочка от Теодора - я вам сейчас объясню, для чего этот фрагмент, а вы не будете меня бить за отсутствие комментариев? Идет? Идет.
Лук, он сразу получает ссылку на пэрэнта - активность, приводя ее к FiltersParentActivity - этот интерфейс специально был разработан, чтобы этот фрагмент мог сказать родительской активности:
"Братик, тебе нужно скрыть место, где я лежу, потому что пользователь что-то тыкнул во мне"
Вот и по такой схеме он работает. Т.е, если в каллбэк летит 0 в первом параметре, то активность просто скрывает этот фрагмент, если же 1 - перезагружает ресайклер новыми элементами
Что же происходит именно в этом фрагменте? Все просто, у нас есть захардкоженая ссылка на главный чекбокс - ну тот, который за всех отвечает.
Зачем она захордкожена? Все также просто - мне было немного лень пилить два типа кнопок - хозяина и раба, поэтому я захордколи и элемент, и ссылку на него
Зачем я ссылку захордкодил? Потому что иначе будут сыпаться NullReferences в событиях делегированных элементов
Ну а дальше дефолтное взаимодействие - есть мутабельная мапка, где ключ - текст определенного фильтра, а значение - булевка, которая указывает на то, включен этот фильтр или нет
Соответственно, эта мапка изменяется от кликов по фильтрам.
Короче, дальше все просто. Основный смысл объяснил :0
 */

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
        skills.forEach {
            if (!filterDict.containsKey(it.exp)) {
                filterDict[it.exp] = false
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
                parent.filterFragmentResulted(2, null)
            } else {
                parent.filterFragmentResulted(
                    1,
                    skills.filter { filterDict.filterValues { it }.containsKey(it.exp) })
            }
        }
        allCheckboxSelected = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            checkBoxes.forEach { it.filterCheck.isChecked = isChecked }
        }
        checkBoxAll = checkAll // Ну да, ссылка. А что вы хотели, если он нулл бросает?
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
                    checkBoxAll.setOnCheckedChangeListener(null)
                    if (!isChecked) {
                        checkBoxAll.isChecked = false
                    } else {
                        if (filterDict.values.all { it }) {
                            checkBoxAll.isChecked = true
                        }
                    }
                    checkBoxAll.setOnCheckedChangeListener(allCheckboxSelected)
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