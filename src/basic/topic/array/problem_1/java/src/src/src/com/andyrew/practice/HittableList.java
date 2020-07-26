package com.andyrew.practice;

import java.util.ArrayList;
import java.util.List;

public class HittableList implements Hittable  {

    List<Hittable> hittableList;

    public List<Hittable> getHittableList() {
        return hittableList;
    }

    public HittableList(List<Hittable> hittableList) {
        this.hittableList = hittableList;
    }

    public HittableList() {
        this.hittableList = new ArrayList<>();
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
        boolean hit_anything = false;
        var closest_so_far = t_max;

        for (final var object : hittableList) {
            if (object.hit(r, t_min, closest_so_far, rec)) {
                hit_anything = true;
                closest_so_far = rec.t;
            }
        }

        return hit_anything;
    }
}
