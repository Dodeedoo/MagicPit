package me.dodeedoo.magicpit.attributes;

import java.util.HashMap;

public class AttributesHandler {

    public static HashMap<String, Attribute> Attributes = new HashMap<>();

    public static void addAttribute(Attribute attribute, String name) { Attributes.put(name, attribute); }

}
