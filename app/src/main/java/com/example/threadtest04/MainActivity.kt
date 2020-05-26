package com.example.threadtest04

import android.os.*
import android.os.AsyncTask.execute
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.text)

        AsyncTaskAAA().also { it.textAAA = text }.execute(1)

    }
}

class AsyncTaskAAA : AsyncTask<Int, Int, Int>() {
    var textAAA: TextView? = null

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Int?): Int {
        //TODO("Not yet implemented")
        var count = 0
        // nullではないことを確認してからcountをnon-null化
        // publishProgress(count)のcountがプリミティブ型なので変換する
        if (params[0] != null) {
            count = params[0]!!
        }
        for (i in 1..100) {
            Log.d("kd>", "${count}")
            publishProgress(count)
            Thread.sleep(10000)
            count += 1
        }
        return 0
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        //Log.d("kd>", "${values[0]}")

        textAAA?.setText("${values[0]}")

    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        val str = "Complete"
        textAAA?.setText(str)

    }

}