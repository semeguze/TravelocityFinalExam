package com.globant.web.data.entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Class to represent the options to be selected in the flow
 *
 * @author Sebastian Mesa
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectOptions {
    private int optionDeparture;
    private int optionReturn;
}
