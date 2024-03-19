package com.epam.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class A{
    public static void main(String[] args) {
        List<IcsCheckRuleContent> list = new ArrayList<>();
        IcsCheckRuleContent a= new IcsCheckRuleContent();
        a.setIsFirst(false);
        IcsCheckRuleContent b= new IcsCheckRuleContent();
        b.setIsFirst(true);
        list.add(a);
        list.add(b);

        list.sort(Comparator.comparing(content -> content.getIsFirst() ? 0 : 1));

        System.out.println(list.get(0).getIsFirst());
    }

}
