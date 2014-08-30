package com.freeneo.survey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.util.Util;

@RequestMapping(value = "/setup")
@Controller
public class SetupController {

	private static final Logger logger = LoggerFactory
			.getLogger(SetupController.class);

	private Connection getConnection() {
		InitialContext cxt;
		Connection conn = null;
		try {
			cxt = new InitialContext();
			DataSource ds = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/freeneoSurvey");
			conn = ds.getConnection();
		} catch (NamingException e) {
			logger.error(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return conn;
	}

	@RequestMapping(value="/reset", method = RequestMethod.GET)
	@ResponseBody
	public String reset(HttpServletRequest request, HttpSession session) {
		
		drop(request, session);
		create(request, session);
		
		return "<meta charset='utf-8'><p>Complete. See log for detail.</p>";
	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	@ResponseBody
	public String create(HttpServletRequest request, HttpSession session) {
		
		User currentUser = (User) session.getAttribute("user");
		
		if( ! Util.isLocal(request) && currentUser.getUserLevel() != "admin"){
			return "You cannot handle setup";
		}
		
		executeSqlFile(this.getClass().getResource("").getPath() + "../../../create-tables.sql");
		logger.info("테이블과 시퀀스 생성함.");
		return "<meta charset='utf-8'><p>Complete. See log for detail.</p>";
	}
	
	@RequestMapping(value="/drop", method = RequestMethod.GET)
	@ResponseBody
	public String drop(HttpServletRequest request, HttpSession session) {
		
		User currentUser = (User) session.getAttribute("user");
		
		if( ! Util.isLocal(request) && currentUser.getUserLevel() != "admin"){
			return "You cannot handle setup";
		}
		
		executeSqlFile(this.getClass().getResource("").getPath() + "../../../drop-tables.sql");
		logger.info("테이블과 시퀀스 드롭함.");
		return "<meta charset='utf-8'><p>Complete. See log for detail.</p>";
	}
	
	private void executeSqlFile(String path){
		String s = new String();
		StringBuffer sb = new StringBuffer();

		try {
			FileReader fr = new FileReader(new File(path));
			// be sure to not have line starting with "--" or "/*" or any other
			// non
			// alphabetical character

			BufferedReader br = new BufferedReader(fr);

			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();

			// here is our splitter ! We use ";" as a delimiter for each request
			// then we are sure to have well formed statements
			String[] inst = sb.toString().split(";");

			Connection c = getConnection();
			Statement st = c.createStatement();

			logger.debug(Util.getPrintr(inst));
			
			for (int i = 0; i < inst.length; i++) {
				// we ensure that there is no spaces before or after the request
				// string
				// in order to not execute empty statements
				if (!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]);
					logger.debug(inst[i]);
				}
			}
			c.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
