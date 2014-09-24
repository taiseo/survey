package com.freeneo.survey;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.Target;
import com.freeneo.survey.domain.TargetGroup;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetGroupMapper;
import com.freeneo.survey.mapper.TargetMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.service.SurveyService;
import com.freeneo.survey.util.Util;

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
	ResponseMapper responseMapper;

	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	TargetMapper targetMapper;

	@Autowired
	TargetGroupMapper targetGroupMapper;

	HashMap<String, String> statusMap;

	SurveyController() {
		this.statusMap = new HashMap<String, String>();
		statusMap.put("temporary", "임시저장"); // 승인요청 전 상태다
		statusMap.put("standby", "승인대기");

		// 심의승인은 사실상 의미가 없다.
		statusMap.put("approval1", "심의승인");
		statusMap.put("approval_completed", "발송대기");
		statusMap.put("sending", "발송");
		statusMap.put("close", "종료");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpSession session){
		return list("1", model, session);
	}
	
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)	
	public String list(
			@PathVariable(value = "page") String page,
			Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");
		logger.debug("user = {}", user);

		List<Survey> list;

		if (user.getUserLevel().equals("일반")) {
			list = surveyMapper.myList(user.getUsername());
		} else {
			list = surveyMapper.list();
		}

		for (Survey survey : list) {
			survey.setDatetime(Util.formating(survey.getDatetime(),
					"##########<br/>#########"));
			survey.setRespondentCount(responseMapper
					.countRespondentBySurveyId(survey.getId()));
		}

		logger.debug("surveys = {}", list);

		PagedListHolder<Survey> pagedListHolder = new PagedListHolder<Survey>(
				list);
		pagedListHolder.setPageSize(10);

		if (page.equalsIgnoreCase("next")) {
			pagedListHolder.nextPage();
		} else if (page.equalsIgnoreCase("prev")) {
			pagedListHolder.previousPage();
		} else if (page.equalsIgnoreCase("first")) {
			pagedListHolder.setPage(0);
		} else if (page.equalsIgnoreCase("last")) {
			pagedListHolder.setPage(pagedListHolder.getPageCount());
		} else {
			pagedListHolder.setPage(Integer.parseInt(page) - 1);
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
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

		List<TargetGroup> targetGroups = targetGroupMapper.list();

		model.addAttribute("targetGroups", targetGroups);
		model.addAttribute("pageTitle", "새 설문");
		model.addAttribute("survey", survey);
		model.addAttribute("httpMethod", "POST");
		return "survey_manage";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertAction(
			Survey survey, 
			Model model, 
			HttpSession session, 
			@RequestParam("excel") MultipartFile file)
			throws JsonParseException, JsonMappingException, IOException {

		logger.debug("survey parameter = {}", survey);

		if (!validateSurvey(survey, model)) {
			logger.debug("필수항목 누락");
			return insertPage(model, survey);
		}

		User currentUser = (User) session.getAttribute("user");
		survey.setStatus("임시저장");
		survey.setWriter(currentUser.getUsername());
		survey.setPart(currentUser.getPart());

		surveyMapper.insert(survey);

		logger.debug("insertedSurvey = {}", survey);

		return "redirect:/surveys/detail/" + survey.getId();
	}

	/**
	 * 설문 입력 검증
	 * 
	 * @param survey
	 * @return
	 */
	private boolean validateSurvey(Survey survey, Model model) {
		if (Util.isEmptyStr(survey.getTitle())) {
			model.addAttribute("error_msg", "제목을 입력해 주세요");
			return false;
		}

		if (Util.isEmptyStr(survey.getEndDate())) {
			model.addAttribute("error_msg", "종료일을 입력해 주세요");
			return false;
		}

		if (Util.isEmptyStr(survey.getMsgSubject())) {
			model.addAttribute("error_msg", "MMS 제목을 입력해 주세요");
			return false;
		}

		if (Util.isEmptyStr(survey.getMsg())) {
			model.addAttribute("error_msg", "MMS 인사말을 입력해 주세요");
			return false;
		}

		if (!survey.getTargetRegistrationType().equals("엑셀파일 업로드")) {

			if (Util.isEmptyStr(survey.getTargetStartDate())) {
				model.addAttribute("error_msg", "계약기간 시작일을 입력해 주세요.");
				return false;
			}
			if (Util.isEmptyStr(survey.getTargetEndDate())) {
				model.addAttribute("error_msg", "계약기간 종료일을 입력해 주세요.");
				return false;
			}

		}

		if (survey.getTargetRegistrationType().equals("CRM DB 추출")) {
			if (Util.isEmptyStr(survey.getTargetBranches())) {
				model.addAttribute("error_msg", "지사를 선택해 주세요");
				return false;
			}
			if (survey.getTargetBranches().equals("[]")) {
				model.addAttribute("error_msg", "지사를 선택해 주세요");
				return false;
			}

		}

		if (survey.getTargetRegistrationType().equals("캠페인 그룹 선택")) {
			if (Util.isEmptyStr(survey.getTargetGroupIds())) {
				model.addAttribute("error_msg", "캠페인 그룹을 선택해 주세요");
				return false;
			}
			if (survey.getTargetGroupIds().equals("[]")) {
				model.addAttribute("error_msg", "캠페인 그룹을 선택해 주세요");
				return false;
			}
		}

		return true;
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

		logger.debug("survey = {}", survey);

		if (survey.getStatus() != null && !survey.getStatus().equals("승인대기")
				&& !survey.getStatus().equals("임시저장")) {
			model.addAttribute("msg", "임시저장이거나 승인대기중인 설문만 수정할 수 있습니다.");
			return list(model, session);
		}

		// 시스템 관리자가 아닌데, 남의 것을 수정하려고 하면
		logger.debug("currentUser.userLevel={}", currentUser.getUserLevel());
		logger.debug("currentUser.username={}", currentUser.getUsername());
		logger.debug("survey.writer={}", survey.getWriter());
		if (!currentUser.getUserLevel().equals("시스템 관리자")
				&& !currentUser.getUsername().equals(survey.getWriter())) {
			model.addAttribute("error_msg", "남의 것은 수정할 수 없습니다.");
			return list(model, session);
		}

		logger.debug("survey.targetBranches = {}", survey.getTargetBranches());

		List<TargetGroup> targetGroups = targetGroupMapper.list();

		model.addAttribute("targetGroups", targetGroups);
		model.addAttribute("targetBranches", survey.getTargetBranches());
		model.addAttribute("pageTitle", survey.getTitle() + " 수정");
		model.addAttribute("survey", survey);
		model.addAttribute("httpMethod", "POST");
		return "survey_manage";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String updateAction(Survey survey, Model model, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {

		User currentUser = (User) session.getAttribute("user");
		Survey oldSurvey = surveyMapper.select(survey.getId());

		logger.debug("new survey = {}", survey);
		logger.debug("old survey = {}", oldSurvey);

		// 시스템 관리자가 아닌데, 남의 것을 수정하려고 하면
		if (!currentUser.getUserLevel().equals("시스템 관리자")
				&& !currentUser.getUsername().equals(oldSurvey.getWriter())) {
			model.addAttribute("error_msg", "남의 것은 수정할 수 없습니다.");
			return list(model, session);
		}

		if (!validateSurvey(survey, model)) {
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
			survey.setStatus("임시저장");
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
		if (!currentUser.getUserLevel().equals("시스템 관리자")
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
			@PathVariable(value = "status") String status,
			HttpServletRequest request, Model model, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {

		status = statusMap.get(status);
		logger.debug("status = {}", status);

		User user = (User) session.getAttribute("user");
		logger.debug("user = {}", user);

		if (user.getUserLevel().equals("일반") && status.contains("승인자")) {
			model.addAttribute("error_msg", "권한이 없습니다.");
			return list(model, session);
		}

		Survey survey = surveyMapper.select(id);
		survey.setId(id);

		// 둘 다 승인하는 시나리오
		if (survey.getStatus().equals("심의승인") && status.equals("승인자2만 승인")) {
			status = "발송대기";
		}

		// 둘 다 승인하는 시나리오
		if (survey.getStatus().equals("승인자2만 승인") && status.equals("심의승인")) {
			status = "발송대기";
		}

		survey.setStatus(status);
		logger.debug("survey to update = {}", survey);

		if (status.equals("발송")) {
			if (!surveyService.sendMms(survey, request, model)) {
				return list(model, session);
			}
		}

		surveyMapper.update(survey);

		return "redirect:/surveys";
	}

	@RequestMapping(value = "/target-count", method = RequestMethod.POST)
	@ResponseBody
	public String targetCount(
			@RequestParam(value = "category1") String category1,
			@RequestParam(value = "category2", required = false, defaultValue = "") String category2,
			@RequestParam(value = "branches") String branches,
			@RequestParam(value = "limit") int limit, 
			@RequestParam(value = "startDate") String startDate, 
			@RequestParam(value = "endDate") String endDate, 
			HttpServletRequest request,
			Model model)
			throws JsonParseException, JsonMappingException, IOException {

		List<Customer> customers = surveyService.customerList(category1, category2, branches, limit, startDate, endDate);

		String linkStr = "";

		if(customers.size() > 0){
			linkStr = "<a href='#' class='js-target-detail'>" + customers.size() + "명 <small>(자세히 보기)</small></a>";
		}else{
			linkStr = "0명";
		}
		
		return linkStr;
	}
	
	@RequestMapping(value = "/targets/{surveyId}", method = RequestMethod.GET)
	public String targets(@PathVariable(value = "surveyId") Long surveyId,
			Model model) {

		Survey survey = surveyMapper.select(surveyId);
		List<Target> targets = targetMapper.selectBySurveyId(surveyId);

		for (Target target : targets) {
			target.setPtcpDttm(Util.formating(target.getPtcpDttm(),
					"####-##-##"));
		}

		model.addAttribute("pageTitle", survey.getTitle() + " 발송 명단");
		model.addAttribute("survey", survey);
		model.addAttribute("targets", targets);

		return "target_list";
	}

	@RequestMapping(value = "/target-detail", method = RequestMethod.POST)
	public String targetDetail(
			@RequestParam(value = "category1") String category1,
			@RequestParam(value = "category2", required = false, defaultValue = "") String category2,
			@RequestParam(value = "branches") String branches,
			@RequestParam(value = "limit") int limit,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			HttpServletRequest request, Model model) throws JsonParseException,
			JsonMappingException, IOException {

		List<Map<String, String>> targetInfosByBranch = new ArrayList<Map<String, String>>();
		List<String> branchList = surveyService.makeBranchList(branches);

		for (String branch : branchList) {
			Map<String, String> targetInfo = new HashMap<String, String>();

			// branch를 json으로 전달해야 한다. 이미 있는 걸 또 만들기 귀찮아서 그냥 json으로 만들어서 넘겨 준다.
			// - ahw 2014-09-24
			List<Customer> customers = surveyService.customerList(category1,
					category2, "[\"" + branch + "\"]", limit, startDate,
					endDate);
			targetInfo.put("branchName", branch);
			targetInfo.put("count", String.valueOf(customers.size()));

			targetInfosByBranch.add(targetInfo);
		}

		model.addAttribute("targetInfosByBranch", targetInfosByBranch);
		model.addAttribute("pageTitle", "지사별 대상수");
		return "target_detail";
	}
}
