package com.billing.Bill.Generation.App.DTO.Response;

public class PaginatedData<T> extends UserResponse<T>{
    private long totalData;
    private int currentPage;
    private int limit;

    public PaginatedData(T data, String message, boolean success, long totalData, int currentPage, int limit) {
        super(data, message, success);
        this.totalData = totalData;
        this.currentPage = currentPage;
        this.limit = limit;
    }

    public long getTotalData() {
        return totalData;
    }

    public void setTotalData(long totalData) {
        this.totalData = totalData;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
