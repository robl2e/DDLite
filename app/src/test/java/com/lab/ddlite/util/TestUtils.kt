package com.lab.ddlite.util


fun getJsonStringFromResource(classLoader: ClassLoader, filename : String): String{
    return classLoader.getResource(filename).readText()
}