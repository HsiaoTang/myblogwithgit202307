package test.myblog.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import test.myblog.persist.dao.DAOException;
import test.myblog.service.SchedulerConfigService;

@Configuration
public class QuartzSchedulerConfig {
	
	@Autowired
	private SchedulerConfigService scs;
	
	@Bean(name = "batchJobScheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger... triggers) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setOverwriteExistingJobs(true);
        bean.setStartupDelay(1);
        bean.setTriggers(triggers);
        return bean;
    }

	@Bean(name="impCsvJobDetail")
    public MethodInvokingJobDetailFactoryBean impCsvJobDetail(BatchJobConfig bjc) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(bjc);
        jobDetail.setTargetMethod("runImportCsv");
        return jobDetail;
    }
    
	@Bean(name="impCsvJobTrigger")
    public CronTriggerFactoryBean impCsvJobTrigger(JobDetail impCsvJobDetail) throws DAOException  { 
        String cron = scs.getTriggerCron(1);
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(impCsvJobDetail);
        trigger.setCronExpression(cron);
        return trigger;
    }
	
	@Bean(name="expCsvJobDetail")
    public MethodInvokingJobDetailFactoryBean expCsvJobDetail(BatchJobConfig bjc) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(bjc);
        jobDetail.setTargetMethod("runExportCsv");
        return jobDetail;
    }
    
	@Bean(name="expCsvJobTrigger")
    public CronTriggerFactoryBean expCsvJobTrigger(JobDetail expCsvJobDetail) throws DAOException { 
        String cron = scs.getTriggerCron(2);
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(expCsvJobDetail);
        trigger.setCronExpression(cron);
        return trigger;
    }
}
