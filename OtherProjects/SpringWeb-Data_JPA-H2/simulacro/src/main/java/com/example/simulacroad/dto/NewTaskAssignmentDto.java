package com.example.simulacroad.dto;




public class NewTaskAssignmentDto {
    private int taskId;
    private int familyMemberId;

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public int getTaskId() {
        return taskId;
    }
}