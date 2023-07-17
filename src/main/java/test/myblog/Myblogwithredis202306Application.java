package test.myblog;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class})
@EnableScheduling
public class Myblogwithredis202306Application {
	

    @Autowired
    JobLauncher jobLauncher;
      
    @Autowired
    @Qualifier("importCsv")
    Job importCsv;
    
    @Autowired
    @Qualifier("exportCsv")
    Job exportCsv;


	public static void main(String[] args) {
		SpringApplication.run(Myblogwithredis202306Application.class, args);
	}
	
	@Scheduled(cron = "0 25 * * * ?")
    public void performImportCsv() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(importCsv, params);
    }
	
	@Scheduled(cron = "0 29 * * * ?")
    public void performExportCsv() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(exportCsv, params);
    }

}
