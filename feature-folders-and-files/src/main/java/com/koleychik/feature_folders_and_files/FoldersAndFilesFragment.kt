package com.koleychik.feature_folders_and_files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.feature_folders_and_files.databinding.FragmentFoldersAndFilesBinding

class FoldersAndFilesFragment : Fragment() {

    private var _binding: FragmentFoldersAndFilesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoldersAndFilesBinding.inflate(inflater, container, false)

        return binding.root
    }
}