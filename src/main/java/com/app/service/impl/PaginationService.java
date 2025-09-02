package com.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    @Value("${default_page_size}")
    private int defaultPageSize;

    public int getDefaultPageSize(int requestSize) {
        System.out.println("DefaultPageSize = > " + defaultPageSize);
        return defaultPageSize >= requestSize ? requestSize : defaultPageSize;
    }


}
