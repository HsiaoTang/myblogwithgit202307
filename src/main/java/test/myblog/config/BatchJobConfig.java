package test.myblog.config;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;

import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.MemberDAO;
import test.myblog.persist.dao.impl.TagDAO;
import test.myblog.model.Article;

@Configuration
@Component
@EnableScheduling
@EnableBatchProcessing
public class BatchJobConfig {
	
	@Autowired
	private MemberDAO md;
	
	@Autowired
	private TagDAO td;
	
	@Autowired
    private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	   
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobLauncher jobLauncher;

	@Bean
	public FlatFileItemReader<Article> csvImpReader() {
		FlatFileItemReader<Article> flatFileItemreader = new FlatFileItemReader<Article>();
		ServletContextResource resource = new ServletContextResource(servletContext, "/batch/input/inputtest.csv");
		flatFileItemreader.setResource(resource);
//	    reader.setLinesToSkip(1);
		flatFileItemreader.setLineMapper(new DefaultLineMapper<>() {
	    	{
	    		setLineTokenizer(
	    			new DelimitedLineTokenizer(",") {
	    				{
		    				setNames(new String[] {"a_id", "a_content", "a_date", "a_likes", "a_title", "a_views", "m_id", "t_id"});
		    			}
	    			}
	    		);
	    		setFieldSetMapper(articleFieldSetMapper());
	    	}
	    });
	    return flatFileItemreader;
    }
	
	public FieldSetMapper<Article> articleFieldSetMapper(){
		return new FieldSetMapper<Article>() {
			@Override
			public Article mapFieldSet(FieldSet fs) throws BindException {
				// TODO Auto-generated method stub
				Article a = new Article();
				a.setA_id(fs.readInt("a_id"));
				a.setA_content(fs.readString("a_content"));
				a.setA_date(fs.readDate("a_date"));
				a.setA_likes(fs.readInt("a_likes"));
				a.setA_title(fs.readString("a_title"));
				a.setA_views(fs.readInt("a_views"));
				try {
					a.setM(md.findOne(fs.readInt("m_id")));
					a.setT(td.findOne(fs.readInt("t_id")));
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return a;
			}
		};
	}
	
    public ItemProcessor<Article, Article> itemImpProcessor(){
        return article -> {
        	return article;
        };
    }
	
	public MyBatisBatchItemWriter<Article> myBatisImpWriter() {
	    return new MyBatisBatchItemWriterBuilder<Article>()
	            .sqlSessionTemplate(sqlSessionTemplate)
	            .statementId("test.myblog.mapper.ArticleMapper.saveArticleWithId")
	            .build();
	}
	
	public Step stepImportCsv() {
		return stepBuilderFactory
				.get("Step Import")
				.<Article, Article>chunk(5)
		        .reader(csvImpReader())
		        .listener(itemReadListener())
		        .processor(itemImpProcessor())
		        .writer(myBatisImpWriter())
		        .listener(itemWriteListener())
		        .build();
	}
	
	@Bean(name="importCsv")
	public Job importCsv() {
		return jobBuilderFactory
				.get("importCsvToDb")
		        .incrementer(new RunIdIncrementer())
		        .start(stepImportCsv())
		        .listener(jobListener())
		        .build();
	}
	
	public void runImportCsv() throws Exception {
	    JobParameters jobParameters = new JobParametersBuilder()
	            .addString("JobID", String.valueOf(System.currentTimeMillis()))
	            .toJobParameters();
	    
	    jobLauncher.run(importCsv(), jobParameters);
	}
	
	public MyBatisCursorItemReader<Article> articleDbReader() {
		return new MyBatisCursorItemReaderBuilder<Article>()
				.sqlSessionFactory(sqlSessionFactory)
				.queryId("test.myblog.mapper.ArticleMapper.findAll")
				.build();
	}
	
	public FieldExtractor<Article> articleFieldExtractor(){
		return new FieldExtractor<Article>() {
			
			@Override
			public Object[] extract(Article a) {
				// TODO Auto-generated method stub
				Object[] aLine = new Object[] {a.getA_id(), a.getA_content(), a.getA_date(), a.getA_likes() ,a.getA_title(), a.getA_views(), a.getM().getM_id(), a.getT().getT_id()};
				return aLine;
			}			
		};
		
	}
	
	public FlatFileItemWriter<Article> csvExpWriter(){
        FlatFileItemWriter<Article> flatFileItemWriter = new FlatFileItemWriter<>();
        ServletContextResource resource = new ServletContextResource(servletContext, "/batch/output/outputtest.csv");
        flatFileItemWriter.setResource(resource);
        flatFileItemWriter.setLineAggregator(
        		new DelimitedLineAggregator<Article>() {
        			{
        				setDelimiter(",");
        				setFieldExtractor(articleFieldExtractor());
        			}
        		});
        flatFileItemWriter.setHeaderCallback(writer -> writer.write("a_id,a_content,a_date,a_likes,a_title,a_views,m_id,t_id"));
        return flatFileItemWriter;
	}
	
	public Step stepExportCsv() {
		return stepBuilderFactory
				.get("Step Export")
				.<Article, Article>chunk(5)
		        .reader(articleDbReader())
		        .listener(itemReadListener())
		        .processor(itemImpProcessor())
		        .writer(csvExpWriter())
		        .listener(itemWriteListener())
		        .build();
	}
	
	@Bean(name="exportCsv")
	public Job exportCsv() {
		return jobBuilderFactory
				.get("exportFromDbToCsv")
		        .incrementer(new RunIdIncrementer())
		        .start(stepExportCsv())
		        .listener(jobListener())
		        .build();
	}
	
	public void runExportCsv() throws Exception {
	    JobParameters jobParameters = new JobParametersBuilder()
	            .addString("JobID", String.valueOf(System.currentTimeMillis()))
	            .toJobParameters();
	    
	    jobLauncher.run(exportCsv(), jobParameters);
	}
	
	public ItemReadListener<Article> itemReadListener() {
		return new ItemReadListener<Article>() {
			
			@Override
			public void beforeRead() {
				// TODO Auto-generated method stub
				System.out.println("Start reading.");
			}

			@Override
			public void onReadError(Exception e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}

			@Override
			public void afterRead(Article a) {
				// TODO Auto-generated method stub
				System.out.println("a_id: " + a.getA_id());
			}
		};
    			
    }
	
	public ItemWriteListener<Article> itemWriteListener() {
		return new ItemWriteListener<Article>() {

			@Override
			public void beforeWrite(List<? extends Article> al) {
				// TODO Auto-generated method stub
				System.out.println("Start reading.");
				System.out.println("a_id: " + al.get(0).getA_id());
				
			}

			@Override
			public void afterWrite(List<? extends Article> al) {
				// TODO Auto-generated method stub
				System.out.println("a_id: " + al.get(al.size() - 1).getA_id());
			}

			@Override
			public void onWriteError(Exception e, List<? extends Article> al) {
				// TODO Auto-generated method stub
				System.out.println("a_id: " + al.get(0).getA_id());
				e.printStackTrace();
				
			}
    		
    	};
	}
	
	public JobExecutionListener jobListener() {
		return new JobExecutionListener() {

			@Override
			public void beforeJob(JobExecution jobExecution) {
				// TODO Auto-generated method stub
				System.out.println("Job starts. id={" + jobExecution.getJobId() + "}");
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				// TODO Auto-generated method stub
				System.out.println("Job ends. id={" + jobExecution.getJobId() + "}");
			}
		};
	}
}
