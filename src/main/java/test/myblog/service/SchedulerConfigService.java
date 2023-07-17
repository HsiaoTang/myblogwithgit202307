package test.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.TriggerDAO;
import test.myblog.batch.model.Trigger;

@Service
public class SchedulerConfigService {
	
	@Autowired
	private TriggerDAO trd;
	
	public String getTriggerCron(Integer trId) throws DAOException {
		Trigger tr = trd.findOne(trId);
		return tr.getTr_sec() + " " + tr.getTr_min() + " " + tr.getTr_hour() + " " + tr.getTr_day() + " " + tr.getTr_month() + " " + tr.getTr_year();
	}
}
