package com.example.realestatear.models;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ParentRvModel {

    public LinkedList<ChildRvModel> list;
    public File name;

    public ParentRvModel(LinkedList<ChildRvModel> list, File name) {
        this.list = list;
        this.name = name;
    }
}
