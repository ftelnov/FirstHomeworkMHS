package com.example.firsthomeworkmhs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_filter.*
import java.io.Serializable

class FilterFragment : Fragment() {
    private lateinit var skills: ArrayList<Skill>
    private lateinit var parent: FiltersParentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            skills = it.getSerializable("skills") as ArrayList<Skill>
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