package com.example.qidianhelper

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class QidianService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        Log.i(TAG, "event: $event")
        event?.let {
            var nodes = event.source?.findAccessibilityNodeInfosByText("福利中心")
            nodes?.forEach { node ->
                clickNode(node)
            }
        }
    }

    override fun  onInterrupt() {
    }

    private fun clickNode(node: AccessibilityNodeInfo) {
        if (!node.isVisibleToUser) {
            return
        }
        Log.d(TAG, "$node")

        if (node.isClickable) {
            Log.d(TAG, "click node: ${node.text}")
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            return
        }

        Log.d(TAG, "tap node: ${node.text}")
        val rect = Rect()
        node.getBoundsInScreen(rect)
        tap(rect.exactCenterX(), rect.exactCenterY())
    }

    private fun tap(x: Float, y: Float) {
        Log.d(TAG, "tap ($x, $y)")

        val builder = GestureDescription.Builder()
        val p = Path()
        p.moveTo(x, y)
        builder.addStroke(StrokeDescription(p, 0L, 800L))
        val gesture = builder.build()
        dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription) {
                super.onCompleted(gestureDescription)
                Log.d(TAG, "gesture onCompleted...")
            }

            override fun onCancelled(gestureDescription: GestureDescription) {
                super.onCancelled(gestureDescription)
                Log.e(TAG, "gesture onCancelled...")
            }
        }, null)
    }

    companion object {
        const val TAG = "QidianService"
    }
}