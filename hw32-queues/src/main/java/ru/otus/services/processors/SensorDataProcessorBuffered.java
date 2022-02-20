package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.DoubleBuffer;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final Set<SensorData> dataBuffer;


    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new TreeSet<>(Comparator.comparing(SensorData::getMeasurementTime));

    }

    @Override
    public void process(SensorData data) {

            if (dataBuffer.size() < bufferSize) {
                try {
                    dataBuffer.add(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                flush();
                dataBuffer.add(data);
            }
    }

    public synchronized void flush() {
        System.out.println(dataBuffer.size());
        while (dataBuffer.size() > 0) {
            try {
                List<SensorData> bufferedData = new ArrayList<>(dataBuffer);
                writer.writeBufferedData(bufferedData);
                dataBuffer.clear();
            } catch (Exception e) {
                log.error("Ошибка в процессе записи буфера", e);
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
