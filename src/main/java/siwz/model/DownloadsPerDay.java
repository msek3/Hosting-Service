package siwz.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity(name = "downloads_per_day")
public class DownloadsPerDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private Long amount;

    @Column
    private LocalDateTime day;

}
