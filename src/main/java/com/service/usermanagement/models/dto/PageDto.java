package com.service.usermanagement.models.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageDto<T> {
    private List<T> items;
    private long pageIndex;
    private long pageSize;
    private long totalPage;
    private long totalItem;

    public PageDto() {
    }

    public PageDto(Page<T> queryResult) {
        this.items = queryResult.getContent();
        this.pageIndex = queryResult.getNumber();
        this.pageSize = queryResult.getSize();
        this.totalPage = queryResult.getTotalPages();
        this.totalItem = queryResult.getTotalElements();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }
}
