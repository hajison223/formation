package com.lifeistech.formation;

import java.util.List;

public class Project {
    List<Dancer> dancers;
    String name;
    List<Integer> chapterTime;

    Project(List<Dancer> dancers, String name,List<Integer>chapterTime) {
        this.dancers = dancers;
        this.name = name;
        this.chapterTime = chapterTime;
    }
}
