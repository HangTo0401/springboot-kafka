package com.example.demo.reader;

import com.example.demo.service.ProducerService;
import com.example.demo.utils.Fields;
import com.example.demo.entity.Message;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

@Component
public class CsvReader {

    private Logger logger = LoggerFactory.getLogger(CsvReader.class);

    @Value("${path.url}")
    private String pathUrl;

    private static final String CRON_EVERY_MINUTE = "0 */1 * * * *";
    private static final String HH_MM_A = "hh:mm a";
    private static final String CSV = ".csv";
    private final ResourceLoader resourceLoader;

    @Autowired
    private ProducerService producerService;

    public CsvReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Scheduled(cron = CRON_EVERY_MINUTE)
    public void readMultipleCsvFiles() {
        File directoryPath = new File(pathUrl);
        File[] files = directoryPath.listFiles();

        if (Objects.nonNull(files) && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            StringBuilder sb = new StringBuilder();

            for (File file : files) {
                if (file.isFile() && StringUtils.isNotBlank(file.getName())
                        && file.getName().contains(CSV)) {
                    SimpleDateFormat sdf = new SimpleDateFormat(HH_MM_A);
                    String dateStr = sdf.format(new Date());
                    sb.append(dateStr).append(" -> read ").append(file.getName().replaceAll(CSV, " ")).append("file. \n");
                    logger.info(sb.toString());

                    // Read this file with the BufferedReader like in your code above
                    readCsvFileAndPublishMessage(file.getName(), producerService::sendMessage);
                    sb.delete(0, sb.length());
                }
            }
        }
    }

    /**
     * Read all data in CSV files line by line and publish to topic
     * */
    public void readCsvFileAndPublishMessage(String filename, Consumer<Message> consume) {
        CsvBeanReader beanReader = null;

        try {
            // Read CSV file
            Resource resource = resourceLoader.getResource("classpath:" + filename);
            beanReader = new CsvBeanReader(new InputStreamReader(resource.getInputStream()), CsvPreference.STANDARD_PREFERENCE);

            // Consume the header line
            beanReader.getHeader(true);
            final CellProcessor[] processors = Fields.getProcessors();

            Message message;
            while( (message = beanReader.read(Message.class, Fields.getFieldMapping(), processors)) != null ) {
                consume.accept(message);
            }
            logger.info("All records in file {} are read", filename);

        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        } catch (IOException e) {
            logger.error("Error while reading file: " +  filename, e);
        } finally {
            closeReader(beanReader);
        }
    }

    private void closeReader(CsvBeanReader csvBeanReader) {
        if(csvBeanReader != null ) {
            try {
                csvBeanReader.close();
            } catch (IOException e) {
                logger.error("Error while closing csv bean reader", e);
            }
        }
    }
}
