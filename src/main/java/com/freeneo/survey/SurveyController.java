package com.freeneo.survey;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.Target;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.service.SurveyService;

@Controller
@RequestMapping(value = "/surveys")
public class SurveyController {

	private static final Logger logger = LoggerFactory
			.getLogger(SurveyController.class);

	@Autowired
	SurveyMapper surveyMapper;

	@Autowired
	SurveyService surveyService;

	@Autowired
	QuestionMapper questionMapper;

	@Autowired
	ResponseItemMapper responseItemMapper;

	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	TargetMapper targetMapper;

	HashMap<String, String> statusMap;

	SurveyController() {
		this.statusMap = new HashMap<String, String>();
		statusMap.put("standby", "대기");
		statusMap.put("approval", "승인");
		statusMap.put("sending", "발송");
		statusMap.put("close", "종료");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");

		List<Survey> list;

		if (!user.getUserLevel().equals("admin")) {
			list = surveyMapper.myList(user.getUsername());
		} else {
			list = surveyMapper.list();
		}

		logger.debug("surveys = {}", list);

		model.addAttribute("list", list);
		model.addAttribute("pageTitle", "설문 목록");
		return "survey_list";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertPage(Model model, Survey survey) {
		if (survey == null) {
			survey = new Survey();
		}

		survey.setStartDate(new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +7);
		survey.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));

		model.addAttribute("pageTitle", "새 설문");
		model.addAttribute("survey", survey);
		model.addAttribute("httpMethod", "POST");
		return "survey_manage";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertAction(Survey survey, Model model, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {

		if (survey.getTitle().equals("") || survey.getEndDate().equals("")
				|| survey.getTargetBranches() == null
				|| survey.getTargetBranches().equals("[]")
				|| survey.getMsgSubject().equals("")
				|| survey.getMsg().equals("")) {
			model.addAttribute("error_msg",
					"필수 항목(제목, 게시종료일, 지사, MMS 제목, MMS 내용)을 모두 입력해 주세요.");
			logger.debug("필수항목을 빠뜨린 경우. 이전 입력 정보를 들고 입력페이지로 감.");
			return insertPage(model, survey);
		}

		User currentUser = (User) session.getAttribute("user");
		survey.setWriter(currentUser.getUsername());
		survey.setPart(currentUser.getPart());

		logger.debug("survey = {}", survey);

		surveyMapper.insert(survey);

		logger.debug("insertedSurvey = {}", survey);

		return "redirect:/surveys/detail/" + survey.getId();
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updatePage(@PathVariable(value = "id") Long id, Model model,
			HttpSession session, Survey survey) throws JsonParseException,
			JsonMappingException, IOException {

		User currentUser = (User) session.getAttribute("user");
		logger.debug("survey={}", survey);

		if (survey.getTitle() == null) {
			logger.debug("DB에 있는 survey를 가져옴.");
			survey = surveyMapper.select(id);
			logger.debug("survey in db={}", survey);
		}
		
		logger.debug("survey={}", survey);

		if (survey.getStatus() != null && !survey.getStatus().equals("대기")) {
			model.addAttribute("msg", "대기중인 설문만 수정할 수 있습니다.");
			return list(model, session);
		}

		// admin이 아닌데, 남의 것을 수정하려고 하면
		logger.debug("currentUser.userLevel={}", currentUser.getUserLevel());
		logger.debug("currentUser.username={}", currentUser.getUsername());
		logger.debug("survey.writer={}", survey.getWriter());
		if (!currentUser.getUserLevel().equals("admin")
				&& !currentUser.getUsername().equals(survey.getWriter())) {
			model.addAttribute("error_msg", "남의 것은 수정할 수 없습니다.");
			return list(model, session);
		}

		List<String> branches = surveyService.makeBranchList(survey
				.getTargetBranches());

		model.addAttribute("branches", branches);
		model.addAttribute("pageTitle", survey.getTitle() + " 수정");
		model.addAttribute("survey", survey);
		model.addAttribute("httpMethod", "PUT");
		return "survey_manage";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public String updateAction(Survey survey, Model model, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {

		User currentUser = (User) session.getAttribute("user");
		Survey oldSurvey = surveyMapper.select(survey.getId());

		logger.debug("old survey = {}", oldSurvey);

		// admin이 아닌데, 남의 것을 수정하려고 하면
		if (!currentUser.getUserLevel().equals("admin")
				&& !currentUser.getUsername().equals(oldSurvey.getWriter())) {
			model.addAttribute("error_msg", "남의 것은 수정할 수 없습니다.");
			return list(model, session);
		}

		if (survey.getTitle().equals("") || survey.getEndDate().equals("")
				|| survey.getTargetBranches() == null
				|| survey.getTargetBranches().equals("[]")
				|| survey.getMsgSubject().equals("")
				|| survey.getMsg().equals("")) {
			model.addAttribute("error_msg",
					"필수 항목(제목, 게시종료일, 지사, MMS 제목, MMS 내용)을 모두 입력해 주세요.");
			logger.debug("필수항목을 빠뜨린 경우. 이전 입력 정보를 들고 업데이트페이지로 감.");
			return updatePage(survey.getId(), model, session, survey);
		}

		if (oldSurvey.getWriter() == null) {
			survey.setWriter(currentUser.getUsername());
		} else {
			survey.setWriter(oldSurvey.getWriter());
		}

		if (oldSurvey.getPart() == null) {
			survey.setPart(currentUser.getPart());
		} else {
			survey.setPart(oldSurvey.getPart());
		}

		if (oldSurvey.getStatus() == null) {
			survey.setStatus("대기");
		} else {
			survey.setStatus(oldSurvey.getStatus());
		}

		logger.debug("new survey = {}", survey);

		surveyMapper.update(survey);

		return "redirect:/surveys/detail/" + survey.getId();
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detailPage(@PathVariable(value = "id") Long id, Model model,
			HttpSession session) {

		User currentUser = (User) session.getAttribute("user");
		Survey survey = surveyMapper.select(id);
		if (!currentUser.getUserLevel().equals("admin")
				&& !currentUser.getUsername().equals(survey.getWriter())) {
			model.addAttribute("error_msg", "남의 것을 편집할 수는 없습니다.");
			return list(model, session);
		}

		List<Question> questions = questionMapper.list(id);

		for (Question question : questions) {
			List<ResponseItem> responseItems = responseItemMapper.list(question
					.getId());
			question.setResponseItems(responseItems);
		}

		logger.debug("questions = {}", questions);

		ObjectMapper objectMapper = new ObjectMapper();
		String questionsString = null;
		try {
			questionsString = objectMapper.writeValueAsString(questions);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		logger.debug("questionsString = {}", questionsString);

		model.addAttribute("survey", survey);
		model.addAttribute("questionsString", questionsString);
		model.addAttribute("pageTitle", survey.getTitle() + " 문항 편집");
		return "survey_detail";
	}

	@RequestMapping(value = "/status/{id}", method = RequestMethod.GET)
	public String status(@PathVariable(value = "id") Long id, Model model) {
		Survey survey = surveyService.getFullSurvey(id);
		model.addAttribute("survey", survey);

		return "survey_status";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Long id) {
		surveyMapper.delete(id);
		return "redirect:/surveys";
	}

	@RequestMapping(value = "/update-status/{id}/{status}", method = RequestMethod.GET)
	public String updateStatus(@PathVariable(value = "id") Long id,
			@PathVariable(value = "status") String status)
			throws JsonParseException, JsonMappingException, IOException {

		status = statusMap.get(status);
		logger.debug("status = {}", status);

		Survey survey = surveyMapper.select(id);
		survey.setId(id);
		survey.setStatus(status);
		logger.debug("survey to update = {}", survey);

		if (status.equals("발송")) {
			surveyService.sendMms(survey);
		}

		surveyMapper.update(survey);

		return "redirect:/surveys";
	}

	@RequestMapping(value = "/target-count", method = RequestMethod.POST)
	@ResponseBody
	public String targetCount(
			@RequestParam(value = "targetCategory1") String targetCategory1,
			@RequestParam(value = "targetCategory2", required = false, defaultValue = "") String targetCategory2,
			@RequestParam(value = "targetBranches") String targetBranches,
			@RequestParam(value = "limit") int limit, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		List<Customer> customers = surveyService.customerList(targetCategory1,
				targetCategory2, targetBranches, limit);

		return "(" + customers.size() + "명)";
	}

	@RequestMapping(value = "/targets/{surveyId}", method = RequestMethod.GET)
	public String targets(
			@PathVariable(value = "surveyId") Long surveyId,
			Model model) {
		
		Survey survey = surveyMapper.select(surveyId);
		List<Target> targets = targetMapper.selectBySurveyId(surveyId);
		
		
		model.addAttribute("pageTitle", survey.getTitle() + " 발송 명단");
		model.addAttribute("survey", survey);
		model.addAttribute("targets", targets);
		
		return "target_list";
	}
}
