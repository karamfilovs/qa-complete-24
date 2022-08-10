package rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Client {
  private String id;
  private String name;
  private String town;
  private String address;
  private boolean is_reg_vat;
  private String mol;
  private String bulstat;

}


