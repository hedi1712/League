package com.example.submission_second
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.submission_second.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val splashTimeOut: Long = 3000
        Handler().postDelayed({ sendToHome() }, splashTimeOut)
    }

    private fun sendToHome() {
        val action = SplashScreenFragmentDirections.actionLaunchTohomeClub()
        findNavController().navigate(action)
    }
}
