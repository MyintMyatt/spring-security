package com.app.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    private T data;
    private Integer current_page;
    private Integer page_size;
    private String sorting_field;
    private String sorting_order;
}
