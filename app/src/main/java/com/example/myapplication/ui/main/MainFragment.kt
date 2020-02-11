package com.example.myapplication.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.SPEECH_REQUEST_CODE
import com.example.myapplication.models.SimpleModel
import com.example.myapplication.recyclerview.RecyclerViewAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var tts: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false).apply {
            btnVoiceControl.setOnClickListener {
                displaySpeechRecognizer()
                btnVoiceControl.isEnabled = false
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        tts = TextToSpeech(context) {
            btnVoiceControl.isEnabled = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecyclerViewAdapter(
                listOf(
                    SimpleModel("Test", "Testowy"),
                    SimpleModel("Fake", "Data"),
                    SimpleModel("Test", "Testowy"),
                        SimpleModel("Fake", "Data"),
                        SimpleModel("Test", "Testowy"),
                        SimpleModel("Fake", "Data"),
                        SimpleModel("Test", "Testowy"),
                        SimpleModel("Fake", "Data")
                )
            )
        }
    }

    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    private fun talkBack(input: String) {
        tts.language = Locale.US
        tts.speak(input.replaceFirst("I want", "You want", true), TextToSpeech.QUEUE_ADD, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE) {
            btnVoiceControl.isEnabled = true
            if (resultCode == Activity.RESULT_OK) {
                val spokenText: String? =
                        data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                            results[0]
                        }
                // Do something with spokenText
                if (!spokenText.isNullOrBlank())
                    talkBack(spokenText)
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
