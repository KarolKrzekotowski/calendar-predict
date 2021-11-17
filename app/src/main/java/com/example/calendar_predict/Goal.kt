package com.example.calendar_predict

import java.time.LocalDate

class Goal (val name: String, val kind: GoalKind, val amountDone: Int, val targetAmount: Int, val expiryDate: LocalDate?) {
    companion object {
        private var taskCount = 0
    }
}