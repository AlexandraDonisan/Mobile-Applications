package en.ubb.server.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "sightseeings")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Sightseeing extends BaseEntity<Integer>{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "schedule", nullable = false)
    private String schedule;

    @Column(name = "price", nullable = false)
    private int price;

}
