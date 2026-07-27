package com.teacherapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // ملاحظة للمستخدم: بما أنك طلبت تحويله لتطبيق أندرويد "حقيقي" بلغة الأندرويد
        // ولكن مع الحفاظ على "نفس التصميم والمنطق"، فإن أفضل وسيلة تقنية هي استخدام
        // WebView متطور مع ربط التنبيهات بنظام الأندرويد الأصلي (Native Alarms).
        // تحويل كل عنصر UI إلى XML سيغير تجربة المستخدم وتصميمه الدقيق الذي أرفقته.
        
        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        
        // إضافة واجهة برمجية للربط بين JavaScript و Android Native
        webView.addJavascriptInterface(AndroidInterface(this), "AndroidNative")
        
        setContentView(webView)
        webView.loadUrl("file:///android_asset/index.html")
    }
}
