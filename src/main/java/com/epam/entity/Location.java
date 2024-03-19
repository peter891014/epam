package com.epam.entity;/*
 *@title Location
 *@description
 *@author ASUS
 *@create 2024/1/16 17:27
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: epam
 * @description:
 * @author: 作者名字
 * @create: 2024-01-16 17:27
 **/
@Data
@AllArgsConstructor
public class Location {

    private double lat;
    private double lon;
}
