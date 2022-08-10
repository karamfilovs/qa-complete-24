package rest.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AllClientResponse {
    private int total;
    private String next_page;
    private String prev_page;
    private List<Client> clients;
}
