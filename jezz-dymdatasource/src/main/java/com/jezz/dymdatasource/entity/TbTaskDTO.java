package com.jezz.dymdatasource.entity;

public class TbTaskDTO {
    private Integer taskId;
    private Integer userId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TbTaskDTO{" +
                "taskId=" + taskId +
                ", userId=" + userId +
                '}';
    }
}
