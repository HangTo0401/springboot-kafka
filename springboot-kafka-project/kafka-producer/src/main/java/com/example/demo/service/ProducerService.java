package com.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Service
public class ProducerService {

    @Value("file.path.url")
    private String PATH;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "0 */1 * * * *")
    public void readFileAndPublishToTopic() throws IOException {
        // Creating a File object for directory
        File directoryPath = new File("src/main/resources/files");

        // List of all files and directories
        File[] files = directoryPath.listFiles();

        // Fetching all the files
        if (Objects.nonNull(files) && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

            for (File file : files) {
                if(file.isFile()) {
                    BufferedReader inputStream = null;
                    String line;

                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    StringBuilder sb = new StringBuilder();

                    try {
                        inputStream = new BufferedReader(new FileReader(file));
                        while ((line = inputStream.readLine()) != null) {
                            sb.append(dateFormat).append(" -> read ").append(line);
                            System.out.println(sb);
                            sb.delete(0, sb.length());
                        }
                    } catch(IOException e) {
                        System.out.println(e);
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
            }
        }
    }
}
