package com.andyrew.practice;

public interface Hittable {
    public boolean hit(final Ray r, final double t_min, final double t_max, HitRecord rec);
}
