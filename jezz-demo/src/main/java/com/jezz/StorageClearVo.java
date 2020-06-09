package com.jezz;

import java.util.List;

public class StorageClearVo {
   private String startDate;
   private String endDate;
   private List<Byte> platforms;
   private List<String> userId;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Byte> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Byte> platforms) {
        this.platforms = platforms;
    }

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }
}
