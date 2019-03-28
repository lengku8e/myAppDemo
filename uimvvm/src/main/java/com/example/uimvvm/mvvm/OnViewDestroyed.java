/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.uimvvm.mvvm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnViewDestroyed {
}
