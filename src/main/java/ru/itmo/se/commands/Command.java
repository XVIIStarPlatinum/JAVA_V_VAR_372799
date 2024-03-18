package ru.itmo.se.commands;

public interface Command {

    String getName();

    String getSpec();

    boolean apply(String arg);
}
