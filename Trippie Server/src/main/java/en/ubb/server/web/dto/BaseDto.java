package en.ubb.server.web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Builder
public class BaseDto implements Serializable {
    private Integer id;
}
