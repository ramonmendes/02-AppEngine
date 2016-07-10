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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cit.lab.model.Client;
import br.com.cit.lab.model.Phone;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import br.com.cit.lab.constant.Constantes;

public class IncluirTelefone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(IncluirTelefone.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		log.info("Create User and Telefone");
		
		String name = request.getParameter(Constantes.NAME);
		String numberPhone = request.getParameter(Constantes.PHONE);
		
		Entity phoneEntity = new Entity(Phone.class.getSimpleName());
		phoneEntity.setProperty(Constantes.PHONE, numberPhone);
		datastore.put(phoneEntity);

		Entity clientEntity = new Entity(Client.class.getSimpleName());
		clientEntity.setProperty(Constantes.NAME, name);
		clientEntity.setProperty(Constantes.PHONE, phoneEntity.getKey());
		datastore.put(clientEntity);
		
		response.setContentType("text/plain");
	    request.getRequestDispatcher("/app/telefone/listar").forward(request, response);

	}
}
