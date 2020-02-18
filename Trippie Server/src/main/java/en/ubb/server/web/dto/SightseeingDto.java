package en.ubb.server.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SightseeingDto extends BaseDto {
    private String name;
    private String schedule;
    private int price;

    @Override
    public String toString() {
        return "SightseeingDto{" +
                "name='" + name + '\'' +
                ", schedule='" + schedule + '\'' +
                ", price=" + price +
                '}';
    }
}
