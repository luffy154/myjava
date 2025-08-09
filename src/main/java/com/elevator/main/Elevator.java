package com.elevator.main;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:Elevator
 * @author: qm
 * @Description:
 * @date:2025-08-06
 */
@Data
@AllArgsConstructor
public class Elevator {
    private String name;
    private int current_floor;
    private String direction;
    private List<Integer> requests;
}
