package com.jezz.session.api;

import java.io.Serializable;

public interface BaseEnum extends Serializable {

    boolean success();

    int getId();

    String getCode();

    String getLabel();
}
