//
// Created by 景彬 on 2017/3/23.
//
#include <stdio.h>
#include <hello.h>
#include "jni.h"

jstring Java_com_example_jingbin_jnidemo_HelloActivity_helloFromC(JNIEnv* env,jobject obj){

    char* str = "hello from c!";

    return (*(*env)).NewStringUTF(env,str);
}

jint Java_com_example_jingbin_jnidemo_HelloActivity_add(JNIEnv* env,jobject obj,jint x,jint y){

    return add(x,y);
}

int add (int x,int y){
  return x+y;
}