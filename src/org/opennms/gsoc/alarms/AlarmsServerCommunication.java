package org.opennms.gsoc.alarms;

import org.opennms.gsoc.model.Alarm;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AlarmsServerCommunication {

    List<Alarm> getAlarms(String url)
            throws InterruptedException, ExecutionException, IOException;

}