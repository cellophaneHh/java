package com.zh.polymorphic;

/**
 * Created by zh on 2017-04-09.
 */
public class CovariantReturn {
    public static void main(String[] args) {
        Mill m = new Mill();
        Grain g = m.process();
        System.out.println(g);
        m = new WheatMill();
        g = m.process();
        System.out.println(g);
    }
}
class Grain {
    public String toString() {
        return "Grain";
    }
}
class Wheat extends Grain {
    public String toString() {
        return "Wheat";
    }
}
class Mill {
    Grain process() {
        return new Grain();
    }
}
class WheatMill extends Mill {
    Wheat process() {
        return new Wheat();
    }
}
