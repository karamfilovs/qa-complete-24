package rest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//DTO/POJO
@AllArgsConstructor
@Setter
@Getter
public class Item {
    private String name;
    private String quantity_unit;
    private double price_for_quantity;
}
