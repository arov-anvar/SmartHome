package com.example.smarthome.utils

import android.content.Context
import android.util.Log
import java.io.*

object FileHelper {
    private val nameFile = "data.json"

    fun writeToFile(
        data: String,
        context: Context
    ) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                context.openFileOutput(
                    nameFile,
                    Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    fun readFromFile(context: Context): String? {
        var outputString = ""
        try {
            val inputStream: InputStream? = context.openFileInput(nameFile)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                outputString = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: $e")
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: $e")
        }
        return outputString
    }
}