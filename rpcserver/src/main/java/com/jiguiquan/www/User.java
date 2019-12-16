package com.jiguiquan.www;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author jigq
 * @create 2019-12-16 20:34
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 4997099209348987343L;   //相当于类的指纹
    private String name;
}
