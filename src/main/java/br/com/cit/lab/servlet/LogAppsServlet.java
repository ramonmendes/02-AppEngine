/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cit.lab.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cit.lab.constant.Constantes;
import br.com.cit.lab.model.LogDataApp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;

public class LogAppsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LogAppsServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String type = request.getParameter(Constantes.TYPE);
		OAuthService authService = OAuthServiceFactory.getOAuthService();
		
		LogDataApp logDataApp;
		try {
			logDataApp = new LogDataApp(authService.getCurrentUser().getEmail(), new Date(), type);
			Entity entity = new Entity(LogDataApp.class.getSimpleName());
			entity.setProperty(Constantes.USER, logDataApp.getUser());
			entity.setProperty(Constantes.DATE, logDataApp.getDate());
			entity.setProperty(Constantes.TYPE, logDataApp.getType());
			datastore.put(entity);
			log.info("info logged " + logDataApp.toString());
		} catch (OAuthRequestException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);;
	}

}
