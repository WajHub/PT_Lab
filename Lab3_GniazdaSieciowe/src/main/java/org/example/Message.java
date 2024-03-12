package org.example;

import java.io.Serializable;

record Message(int number, String content) implements Serializable {}
