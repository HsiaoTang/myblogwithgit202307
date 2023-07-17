package test.myblog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test.myblog.model.Draft;
import test.myblog.persist.dao.DAOException;
import test.myblog.service.DraftService;

@RestController
@RequestMapping("/api/v1/draft")
@CrossOrigin(origins="http://localhost:5173")
public class DraftController {
	
	@Autowired
	private DraftService ds;
	
	@PostMapping("/saveAsDraft")
	public ResponseEntity<Draft> saveAsDraft(@RequestBody Map<String, Object> draftInfo) throws DAOException, IOException {
		return ResponseEntity.ok(ds.createNewDraft(draftInfo));
	}
	
	@PostMapping("/getDraftList")
	public ResponseEntity<List<Map<String, Object>>> getDraftList(@RequestParam("jwt") String jwt) throws DAOException {
		return ResponseEntity.ok(ds.getDraftListByJwt(jwt));
	}
	
	@PostMapping("/deleteDraft")
	public ResponseEntity<List<Map<String, Object>>> deleteDraft(@RequestBody Map<String, Object> draftInfo) throws DAOException {
		return ResponseEntity.ok(ds.deleteDraftAndReturnDraftList(draftInfo));
	}
}
