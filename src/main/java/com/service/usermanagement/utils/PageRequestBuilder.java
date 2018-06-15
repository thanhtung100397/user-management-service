package com.service.usermanagement.utils;

import com.service.usermanagement.constants.Constants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageRequestBuilder {
    private List<Sort.Order> orders;
    private int pageIndex = 0;
    private int pageSize = Constants.MAX_PAGE_SIZE;

    public PageRequestBuilder() {
        orders = new ArrayList<>(1);
    }

    public PageRequestBuilder sort(String sortBy, String sortType) {
        if(sortBy != null && sortType != null) {
            Sort.Direction direction;
            switch (sortType) {
                case Constants.DESC: {
                    direction = Sort.Direction.DESC;
                }
                break;

                default: {
                    direction = Sort.Direction.ASC;
                }
                break;
            }
            orders.add(new Sort.Order(direction, sortBy));
        }
        return this;
    }

    public PageRequestBuilder page(int pageIndex, int pageSize) {
        if(pageIndex >= 0 && pageSize >= 1) {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
        return this;
    }

    public Pageable build() {
        if(!orders.isEmpty()) {
            return PageRequest.of(pageIndex, pageSize, Sort.by(orders));
        }
        return PageRequest.of(pageIndex, pageSize);
    }
}
