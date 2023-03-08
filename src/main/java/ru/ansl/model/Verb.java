package ru.ansl.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Verb {
    private String present;
    private String past;
    private String participle;
    private String translate;

    @Override
    public String toString() {
        return present + " ".repeat(4) +
                past + " ".repeat(4)  +
                participle + " ".repeat(4)  + translate;
    }
}
