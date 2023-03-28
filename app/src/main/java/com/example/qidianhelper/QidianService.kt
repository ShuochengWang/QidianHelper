package com.example.qidianhelper

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

class QidianService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
    }

    override fun  onInterrupt() {
    }
}