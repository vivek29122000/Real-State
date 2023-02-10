package com.example.realestatear.models;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class ChildRvModel {

    public File[] ar;
    public File parent;

    public ChildRvModel(File[] ar, File parent) {
        this.ar = ar;
        this.parent = parent;
    }
}
