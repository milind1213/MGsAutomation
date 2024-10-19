package com.mgs.Pages.RestPage.POJO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Products {
    private int id;
    private String category;
    private String name;
    private boolean inStock;
}
