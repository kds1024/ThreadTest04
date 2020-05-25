package com.example.threadtest04

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {
    // 1.別スレッド(HandlerThread)を生成
    val simplethread = HandlerThread("simplethread")
    var simplehandler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 別スレッドからMainActivityにメッセージを送信するためのハンドラー
        // 
        val mhandler : Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what){
                    1 -> Log.d("kd>", "what:1")
                }
            }
        }

        // 2.別スレッド開始
        simplethread.start()

        // 3.Handlerインスタンスを生成(2.で作成したthreadのLooperインスタンスを引数指定)
        // スレッドを#start()しないとlooperは使えない（初期化されずlooperがnullのままで例外がスローされる)
        // 別スレッドで処理させたいタスクをpostするハンドラー
        simplehandler = Handler(simplethread.looper)

        //Log.d("kd>", "simplelooper ${simplethread.looper}")
        //Log.d("kd>", "simplehandler ${simplehandler}")

        // 4. 3で作成したHandlerインスタンスを使用(Handler#post 等)
        // Runnableをスレッドにpostするとこのタスクをスレッドで実行してくれる
        val r = Runnable {
            Log.d("kd>", "タスク開始 ${HandlerThread.currentThread()}")
            HandlerThread.sleep(3000)
            Log.d("kd>", "タスク完了 ${HandlerThread.currentThread()}")

            // スレッド内からMainActivityにメッセージを送信する
            // これで、スレッドの処理が完了したことがMainActivityに通知できる
            val msg = mhandler.obtainMessage(1, "test")
            mhandler.sendMessage(msg)
        }

        // simplethreadのハンドラーにRunnableをポストして別スレッドでタスクを実行する
        simplehandler.post(r)
    }
}
