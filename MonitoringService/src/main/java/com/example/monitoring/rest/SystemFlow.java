package com.example.monitoring.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Collection;
import java.util.Queue;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SystemFlow {
    Collection<Process> systemsProcesses;

    public SystemFlow() {
    }

    public SystemFlow(Queue<Process> queue) {
        this.systemsProcesses = queue;
    }

    @Override
    public String toString() {
        return "SystemFlow{" + "\n"
                + "systemsProcesses=" + systemsProcesses + "\n"
                + '}';
    }
}
