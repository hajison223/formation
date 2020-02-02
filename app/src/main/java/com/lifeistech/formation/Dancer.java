package com.lifeistech.formation;

import java.util.List;

public class Dancer {
    List<Position> movePositions;
    String name;

    Dancer(List<Position> movePositions,String name){
        this.movePositions = movePositions;
        this.name = name;
    }
}
