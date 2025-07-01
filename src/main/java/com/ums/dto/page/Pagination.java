package com.ums.dto.page;

import lombok.Data;

@Data
public class Pagination {
    private int pageNo = 1;
    private int pageSize = 20;
}
