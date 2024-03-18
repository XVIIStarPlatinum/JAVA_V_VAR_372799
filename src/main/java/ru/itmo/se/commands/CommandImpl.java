package ru.itmo.se.commands;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class CommandImpl implements Command {

    private final String name;

    private final String spec;

    public CommandImpl(String name, String spec) {
        this.name = name;
        this.spec = spec;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CommandImpl command = (CommandImpl) obj;
        return Objects.equals(name, command.name) && Objects.equals(spec, command.spec);
    }

}
