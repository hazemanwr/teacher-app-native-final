package com.teacherapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import java.util.*

class AndroidInterface(private val context: Context) {

    @JavascriptInterface
    fun setNativeReminder(id: Long, groupName: String, timeStr: String, dayName: String, leadMinutes: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        // تحويل الوقت واليوم إلى Calendar
        val calendar = Calendar.getInstance()
        val parts = timeStr.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.add(Calendar.MINUTE, -leadMinutes)
        
        // تحديد اليوم
        val daysMap = mapOf(
            "الأحد" to Calendar.SUNDAY,
            "الاثنين" to Calendar.MONDAY,
            "الثلاثاء" to Calendar.TUESDAY,
            "الأربعاء" to Calendar.WEDNESDAY,
            "الخميس" to Calendar.THURSDAY,
            "الجمعة" to Calendar.FRIDAY,
            "السبت" to Calendar.SATURDAY
        )
        
        val targetDay = daysMap[dayName] ?: return
        while (calendar.get(Calendar.DAY_OF_WEEK) != targetDay || calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("groupName", groupName)
            putExtra("time", timeStr)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context, id.toInt(), intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
