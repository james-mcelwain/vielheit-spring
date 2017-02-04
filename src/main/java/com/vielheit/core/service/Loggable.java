package com.vielheit.core.service;

import org.apache.log4j.Logger;

public interface Loggable {

    default Logger getLogger() {
        return Logger.getLogger(this.getClass());
    }
}
