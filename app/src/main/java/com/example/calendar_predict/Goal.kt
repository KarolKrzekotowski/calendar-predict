package com.example.calendar_predict

import GoalKind

class Goal (val name: String, val kind: GoalKind, val amountDone: Int, val targetAmount: Int) {
    companion object {
        private var taskCount = 0
    }
}