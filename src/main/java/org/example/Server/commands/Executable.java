package org.example.Server.commands;

import org.example.global.facility.Response;
import org.example.global.facility.Mclass;

public interface Executable {
    Response apply(String[] arguments , Mclass mclass);
}
