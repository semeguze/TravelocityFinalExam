package com.globant.web.data.entities;

import lombok.*;

/**
 * Class to represent a hotel
 *
 * @author Sebastian Mesa
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    private String name;
    private String place;
    private String score;
    private String price;
}
