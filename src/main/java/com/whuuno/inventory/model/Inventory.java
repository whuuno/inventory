package com.whuuno.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private Long id;
    private String pdtName;
    private Integer quantity;
    private Integer pdtPrice;
}
