package kl.project.webApp.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "notice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "body")
    private String body;
    @Column(name = "creation_time")
    private LocalDate creationTime;
    @Column(name = "deadline")
    private LocalDate deadline;
}
