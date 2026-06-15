package com.ssw.blog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Type implements Serializable {

    private static final long serialVersionUID = -952315810554536348L;

    private Integer typeId;

    private String typeName;
}
