package com.example.deepseacalamari;

public class Calamari {
    private int hunger, hygiene, fun, energy, level;
    private static final int maxLevel = 5;
    private static final int MAX = 100;
    private static final int MIN = 1;
    private boolean isSleeping;
    private boolean isMaxLevel = false;

    public Calamari() {
        //Starting Values
        this.hunger = MIN;
        this.hygiene = MIN;
        this.fun = MIN;
        this.energy = MAX;
        this.level = 1;
    }

    //LEVELLING SYSTEM
    public int getLevel() {
        return level;
    }

    public void levelUp() {
        if (level < maxLevel) {
            level++;
        } if (level == maxLevel) {
            isMaxLevel = true;
        }
    }

    public boolean isMaxLevel() {
        return isMaxLevel;
    }

    //HUNGER
    public int getHunger() {
        return hunger;
    }

    public void feed() {
        if (hunger < MAX) {
            hunger = Math.min(hunger + 10, MAX);
        }
    }

    public void decreaseHunger() {
        if (hunger > MIN) {
            hunger = Math.max(hunger - 5, MIN);
        }
    }

    //HYGIENE
    public int getHygiene() {
        return hygiene;
    }

    public void bath() {
        if (hygiene < MAX) {
            hygiene = Math.min(hygiene + 30, MAX);
        }
    }

    public void decreaseHygiene() {
        if (hygiene > MIN) {
            hygiene = Math.max(hygiene - 5, MIN);
        }
    }

    //FUN
    public int getFun() {
        return fun;
    }

    public void play() {
        if (fun < MAX) {
            fun = Math.min(fun + 20, MAX);
        }
    }

    public void decreaseFun() {
        if (fun > MIN) {
            fun = Math.max(fun - 5, MIN);
        }
    }

    //ENERGY
    public int getEnergy() {
        return energy;
    }

    public void sleep() {
        isSleeping = true;
        if (energy < MAX) {
            energy = Math.min(energy + 1, MAX);
        }
    }

    public void WakeUp() {
        isSleeping = false;
    }

    public boolean isSleeping(){
        return isSleeping;
    }

    public void decreaseEnergy() {
        if (energy > MIN) {
            energy = Math.max(energy - 5, MIN);
        }
    }

}
