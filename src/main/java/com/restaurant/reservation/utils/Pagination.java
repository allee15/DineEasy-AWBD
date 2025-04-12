package com.restaurant.reservation.utils;

import lombok.Getter;

import java.util.List;

public class Pagination<T> {
    private final List<T> data;

    @Getter
    private final int currentPage;
    private final int pageSize;

    @Getter
    private final int totalPages;
    private final boolean hasNextPage;
    private final boolean hasPreviousPage;

    public Pagination(List<T> data, int currentPage, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
        this.currentPage = currentPage;

        this.totalPages = (int) Math.ceil((double) data.size() / pageSize);

        this.hasNextPage = currentPage < totalPages;
        this.hasPreviousPage = currentPage > 1;
    }

    public List<T> getCurrentPageData() {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, data.size());
        return data.subList(startIndex, endIndex);
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

    public int nextPage() {
        return hasNextPage ? currentPage + 1 : currentPage;
    }

    public int previousPage() {
        return hasPreviousPage ? currentPage - 1 : currentPage;
    }
}
