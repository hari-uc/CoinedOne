package com.hari.coinedone

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Browser
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import java.util.ArrayList

class MyAccessibilityService : AccessibilityService() {


    override fun onServiceConnected() {
        val info  : AccessibilityServiceInfo? = serviceInfo
        if (info != null) {
            info.eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            info.packageNames = arrayOf("com.android.chrome")
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_VISUAL

            info.notificationTimeout = 300
            info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS ; AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
            this.serviceInfo = info // = infoo wanats to delete
        }

    }

    private fun captureUrl(info : AccessibilityNodeInfo) : String {
        var nodes: List<AccessibilityNodeInfo>? = info.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar")
        if (nodes ==null || nodes.size <= 0){
            return ""
        }

        var addressbarnodeinfo : AccessibilityNodeInfo =  nodes.get(0)
        var url: String = "null"
        if (addressbarnodeinfo.text != null){
            url = addressbarnodeinfo.text.toString()
        }
        addressbarnodeinfo.recycle()
        return url

    }




    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        val parentnodeinfo : AccessibilityNodeInfo = event?.source ?: return

        if (parentnodeinfo == null){
            return
        }

        val packname : String = parentnodeinfo.packageName.toString()
        //event.packageName.toString()

        val captureurl : String = captureUrl(parentnodeinfo)
        parentnodeinfo.recycle()

        val eventtime : Long = event?.eventTime ?: return

        val detectionId : String =  " ${packname} and url ${captureurl}"

        if (eventtime > 5000){
            analyzeCapturedurl(captureurl,packname)
        }
        print(detectionId)





    }
    private fun analyzeCapturedurl(@NonNull capturedurl: String , @NonNull browserpackage : String) {
        var redirurl: String = "https://hari-uc.github.io/ErrorMsgPage/"
//
//        val blockedsites: ArrayList<String> = ArrayList()
//
//        blockedsites.add("twitter")
//        blockedsites.add("instagram")
//        blockedsites.add("reddit")
//        blockedsites.add("9gag.com")


        if (capturedurl.contains("facebook.com")) {
            performRedirect(redirurl,browserpackage)

        }
    }

    private fun performRedirect(redirurl: String, browserpackage: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirurl))
            intent.setPackage(browserpackage)
            intent.putExtra(Browser.EXTRA_APPLICATION_ID,"com.android.chrome")
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }catch (e : ActivityNotFoundException){
            val intentt = Intent(Intent.ACTION_VIEW , Uri.parse(redirurl))
            intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentt)

        }

    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }




}